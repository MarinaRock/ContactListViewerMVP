package ru.marina.contactlistviewermvp.presenter.callback

import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.presenter.base.BaseCallback
import ru.marina.contactlistviewermvp.ui.ErrorEvent

interface ContactInfoCallback : BaseCallback {
    fun onDataLoaded(contact: Contact)
    fun onDataError(errorEvent: ErrorEvent)
}