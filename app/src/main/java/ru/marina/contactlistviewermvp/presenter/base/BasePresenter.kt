package ru.marina.contactlistviewermvp.presenter.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter

abstract class BasePresenter<T : BaseCallback> : MvpPresenter<T>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    protected fun Disposable.disposeLater() {
        compositeDisposable.add(this)
    }
}