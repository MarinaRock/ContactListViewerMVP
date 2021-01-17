package ru.marina.contactlistviewermvp.ui.fragment.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Observable
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.databinding.FragmentContactsBinding
import ru.marina.contactlistviewermvp.di.DI
import ru.marina.contactlistviewermvp.presenter.callback.ContactsCallback
import ru.marina.contactlistviewermvp.presenter.contacts.ContactsPresenter
import ru.marina.contactlistviewermvp.ui.ErrorEvent
import ru.marina.contactlistviewermvp.ui.adapter.ContactsAdapter
import ru.marina.contactlistviewermvp.ui.extensions.disable
import ru.marina.contactlistviewermvp.ui.extensions.enable
import ru.marina.contactlistviewermvp.ui.extensions.getMsgFromError
import ru.marina.contactlistviewermvp.ui.extensions.showSnackBar
import ru.marina.contactlistviewermvp.ui.fragment.base.BaseFragment
import toothpick.Toothpick

class ContactsFragment : BaseFragment<FragmentContactsBinding>(),
    ContactsCallback,
    ContactsAdapter.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentContactsBinding =
        FragmentContactsBinding::inflate

    @InjectPresenter
    lateinit var presenter: ContactsPresenter

    @ProvidePresenter
    fun providePresenter(): ContactsPresenter =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(ContactsPresenter::class.java)

    private lateinit var adapter: ContactsAdapter

    private var isContactsReloaded = true
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ContactsAdapter()
        adapter.setOnItemClickListener(this)

        presenter.getContactsWithCache()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener(this)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recycleView.layoutManager = linearLayoutManager
        binding.recycleView.adapter = adapter

        initSearchView()
    }

    private fun initSearchView() {
        val searchViewText: SearchView.SearchAutoComplete =
            binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchViewText.isSaveEnabled = false
        binding.searchView.setQuery(query, false)
        val observable: Observable<String> = Observable.create { emitter ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        emitter.onNext(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        emitter.onNext(newText)
                    }
                    return true
                }
            })
        }
        presenter.searchContacts(observable)
    }

    private fun initList(contacts: PagedList<Contact>) {
        adapter.submitList(contacts)
    }

    override fun onRefresh() {
        isContactsReloaded = true
        presenter.getContacts()
    }

    override fun onItemClick(contact: Contact) {
        findNavController().navigate(ContactsFragmentDirections.toContactInfoFragment(contact.id))
    }

    override fun onDataLoaded(contacts: PagedList<Contact>) {
        binding.searchView.enable()
        initList(contacts)
    }

    override fun onDataError(errorEvent: ErrorEvent) {
        binding.searchView.enable()
        showSnackBar(getMsgFromError(errorEvent))
    }

    override fun showProgress() {
        if (isContactsReloaded) {
            binding.searchView.disable()
            isContactsReloaded = false
        }
        super.showProgress()
    }
}