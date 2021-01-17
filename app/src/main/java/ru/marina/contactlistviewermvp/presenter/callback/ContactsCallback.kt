package ru.marina.contactlistviewermvp.presenter.callback

import androidx.paging.PagedList
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.presenter.base.BaseCallback
import ru.marina.contactlistviewermvp.ui.ErrorEvent

interface ContactsCallback : BaseCallback {
    fun onDataLoaded(contacts: PagedList<Contact>)
    fun onDataError(errorEvent: ErrorEvent)
}