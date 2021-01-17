package ru.marina.contactlistviewermvp.presenter.contacts

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.marina.contactlistviewermvp.CONST.LIST_PAGE_SIZE
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.executor.MainThreadExecutor
import ru.marina.contactlistviewermvp.presenter.base.BasePresenter
import ru.marina.contactlistviewermvp.presenter.callback.ContactsCallback
import ru.marina.contactlistviewermvp.repository.ContactsRepository
import ru.marina.contactlistviewermvp.ui.ErrorEvent
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactsPresenter @Inject constructor(
    private val contactsRepository: ContactsRepository
) : BasePresenter<ContactsCallback>() {

    fun getContactsWithCache() {
        contactsRepository.getContactsWithCache()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .doFinally { viewState.hideProgress() }
            .subscribe(getContactsSingleObserver())
    }

    fun getContacts() {
        contactsRepository.getContacts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .doFinally { viewState.hideProgress() }
            .subscribe(getContactsSingleObserver())
    }

    private fun getContactsSingleObserver() = object : DisposableSingleObserver<List<Contact>>() {
        override fun onSuccess(t: List<Contact>) {
            viewState.onDataLoaded(getPagingContactsList(t))
        }

        override fun onError(e: Throwable) {
            viewState.onDataError(ErrorEvent(e))
        }
    }

    private fun getPagingContactsList(contacts: List<Contact>): PagedList<Contact> {
        val pagedConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(LIST_PAGE_SIZE)
            .build()

        return PagedList.Builder(object : PositionalDataSource<Contact>() {
            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<Contact>
            ) {
                callback.onResult(
                    contacts.subList(
                        params.requestedStartPosition,
                        if (contacts.size < params.requestedLoadSize) contacts.size else params.requestedLoadSize
                    ), 0
                )
            }

            override fun loadRange(
                params: LoadRangeParams,
                callback: LoadRangeCallback<Contact>
            ) {
                callback.onResult(
                    contacts.subList(
                        params.startPosition,
                        if (params.loadSize + params.startPosition >= contacts.size) contacts.size else params.loadSize + params.startPosition
                    )
                )
            }
        }, pagedConfig)
            .setNotifyExecutor(MainThreadExecutor())
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    fun searchContacts(observable: Observable<String>) {
        val disposable: Disposable = observable
            .map {
                viewState.showProgress()
                return@map it
            }
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                contactsRepository.getSearchContacts(it).toObservable()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Contact>>() {
                override fun onNext(t: List<Contact>) {
                    viewState.hideProgress()
                    viewState.onDataLoaded(getPagingContactsList(t))
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                    viewState.hideProgress()
                    viewState.onDataError(ErrorEvent(e))
                }
            })
        compositeDisposable.add(disposable)
    }
}