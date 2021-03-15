package ru.marina.contactlistviewermvp.ui.fragment.contacts

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.marina.contactlistviewermvp.data.model.Contact
import ru.marina.contactlistviewermvp.databinding.FragmentContactInfoBinding
import ru.marina.contactlistviewermvp.di.DI
import ru.marina.contactlistviewermvp.presenter.callback.ContactInfoCallback
import ru.marina.contactlistviewermvp.presenter.contacts.ContactInfoPresenter
import ru.marina.contactlistviewermvp.ui.ErrorEvent
import ru.marina.contactlistviewermvp.ui.extensions.getMsgFromError
import ru.marina.contactlistviewermvp.ui.extensions.showSnackBar
import ru.marina.contactlistviewermvp.ui.fragment.base.BaseFragment
import ru.marina.contactlistviewermvp.util.DateUtils
import ru.marina.contactlistviewermvp.util.Intents
import toothpick.Toothpick

class ContactInfoFragment : BaseFragment<FragmentContactInfoBinding>(), ContactInfoCallback {

    private val args: ContactInfoFragmentArgs by navArgs()

    @InjectPresenter
    lateinit var presenter: ContactInfoPresenter

    @ProvidePresenter
    fun providePresenter(): ContactInfoPresenter =
        Toothpick.openScope(DI.APP_SCOPE).getInstance(ContactInfoPresenter::class.java)

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentContactInfoBinding =
        FragmentContactInfoBinding::inflate

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Intents.callPhone(requireContext(), contact.phone)
            } else {
                Intents.openPermissionsSetting(requireContext())
            }
        }

    lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.getContactById(args.contactId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.contactPhoneTextView.setOnClickListener {
            requestPermission.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private fun initViews(contact: Contact) {
        binding.contactNameTextView.text = contact.name
        binding.contactPhoneTextView.text = contact.phone
        binding.contactTemperamentTextView.text = contact.temperament.name.capitalize()
        val period = DateUtils.getStandardDate(contact.educationPeriod.start) +
                " - " +
                DateUtils.getStandardDate(contact.educationPeriod.end)
        binding.contactEducationDatesTextView.text = period
        binding.contactBiographyTextView.text = contact.biography

        this.contact = contact
    }

    override fun onDataLoaded(contact: Contact) {
        initViews(contact)
    }

    override fun onDataError(errorEvent: ErrorEvent) {
        showSnackBar(getMsgFromError(errorEvent))
    }
}