package ru.marina.contactlistviewermvp.presenter.contacts

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.presenter.base.BasePresenter
import ru.marina.contactlistviewermvp.presenter.callback.ContactInfoCallback
import ru.marina.contactlistviewermvp.repository.ContactsRepository
import ru.marina.contactlistviewermvp.ui.ErrorEvent
import javax.inject.Inject

class ContactInfoPresenter @Inject constructor(
    private val contactsRepository: ContactsRepository
) : BasePresenter<ContactInfoCallback>() {

    fun getContactById(id: String) {
        contactsRepository.getContactById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Contact>() {
                override fun onSuccess(t: Contact) {
                    viewState.onDataLoaded(t)
                }

                override fun onError(e: Throwable) {
                    viewState.onDataError(ErrorEvent(e))
                }
            })
    }
}