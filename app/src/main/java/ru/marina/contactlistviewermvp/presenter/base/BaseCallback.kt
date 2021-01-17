package ru.marina.contactlistviewermvp.presenter.base

import moxy.MvpView

interface BaseCallback : MvpView {
    fun showProgress()
    fun hideProgress()
}