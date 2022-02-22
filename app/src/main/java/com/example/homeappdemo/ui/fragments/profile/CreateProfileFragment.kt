package com.example.homeappdemo.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.FragmentCreateProfileBinding
import com.example.homeappdemo.model.user.UserModel
import com.example.homeappdemo.ui.general.DatePickerBottomSheetFragment
import com.example.homeappdemo.ui.general.showDialog
import com.example.homeappdemo.ui.general.showNegativeDialog
import com.example.homeappdemo.util.SingleLiveEvent
import com.example.homeappdemo.util.Validator
import com.example.homeappdemo.util.extensions.*
import com.example.homeappdemo.util.observeChange
import java.util.*

class CreateProfileFragment : Fragment() {
    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels()
    private var isDatePicked = false
    private val datePattern by lazy { "EEE, dd MMM yyyy" }
    lateinit var user: UserModel
    private val requiredDate: Long by lazy {
        val requiredDate = Date()
        requiredDate.time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        loadViewState()
        setObservers()
        setClickListeners()
    }

    private fun loadViewState() {
        showLoading(false)
        if (viewModel.getAccountStatus()) {
            binding.btnCreateUser.text = getString(R.string.update_account)
            updateUserFields()
        }
    }

    private fun setObservers() {
        viewModel.showLoading.observeChange(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.createAccountListener.observeChange(viewLifecycleOwner) {
            showSuccess(getString(R.string.account_created))
        }

        viewModel.updateAccountListener.observeChange(viewLifecycleOwner) {
            showSuccess(getString(R.string.account_updated))
        }

        DatePickerBottomSheetFragment.onDatePicked.observeChange(viewLifecycleOwner) {
            isDatePicked = true
            binding.etDOB.setText(
                getPatternFromDate(
                    date = it, outputPattern = datePattern,
                    inputPattern = DefaultDateConfiguration.defaultDateFormat
                )
            )
        }
    }

    private fun setClickListeners() {
        binding.btnCreateUser.setOnClickListener {
            if (checkFields()) {
                if (!viewModel.getAccountStatus()) createAccount() else updateAccount()
            } else showError(getString(R.string.fields_required))
        }

        binding.btnOpenDatePicker.setOnClickListener {
            val action =
                CreateProfileFragmentDirections.navigateToDatePickerBottomSheetFragment(date = requiredDate)
            findNavController().navigate(action)
        }
    }

    private fun updateUserFields() {
        viewModel.getUser()?.observeChange(viewLifecycleOwner) {
            setAccountFields(it)
        }
    }

    private fun setAccountFields(user: UserModel) {
        binding.etFirstName.setText(user.firstName)
        binding.etLastName.setText(user.lastName)
        binding.etStreet.setText(user.address.street)
        binding.etCity.setText(user.address.city)
        binding.etCountry.setText(user.address.country)
        binding.etDOB.setText(user.birthDate)
        binding.etPostalCode.setText(user.address.postalCode.toString())
        binding.etStreetCode.setText(user.address.streetCode)
        isDatePicked = true
    }

    private fun createAccount() {
        val mAddress = viewModel.createAddress(
            city = binding.etCity.text.toString(),
            postalCode = binding.etPostalCode.text.toString(),
            country = binding.etCountry.text.toString(),
            streetCode = binding.etStreetCode.text.toString(),
            street = binding.etStreet.text.toString()
        )

        val user = UserModel(
            address = mAddress,
            firstName = binding.etFirstName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            birthDate = binding.etDOB.text.toString()
        )

        this.user = user
        viewModel.createUser(user)
    }

    private fun updateAccount() {
        val mAddress = viewModel.createAddress(
            city = binding.etCity.text.toString(),
            postalCode = binding.etPostalCode.text.toString(),
            country = binding.etCountry.text.toString(),
            streetCode = binding.etStreetCode.text.toString(),
            street = binding.etStreet.text.toString()
        )

        val user = UserModel(
            address = mAddress,
            firstName = binding.etFirstName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            birthDate = binding.etDOB.text.toString()
        )
        this.user = user
        viewModel.updateUser(user)
    }

    private fun checkFields(): Boolean {
        binding.etFirstName.doAfterTextChanged {
            Validator.isValidName(binding.etFirstName)
        }
        binding.etLastName.doAfterTextChanged {
            Validator.isValidName(binding.etLastName)
        }
        binding.etStreet.doAfterTextChanged {
            Validator.isValidStreetName(binding.etStreet)
        }

        binding.etCity.doAfterTextChanged {
            Validator.isValidCityName(binding.etCity)
        }

        binding.etCountry.doAfterTextChanged {
            Validator.isValidCountryName(binding.etCountry)
        }

        binding.etStreetCode.doAfterTextChanged {
            Validator.isValidStreetCode(binding.etStreetCode)
        }

        binding.etPostalCode.doAfterTextChanged {
            Validator.isValidPostCode(binding.etPostalCode)
        }

        return (Validator.isValidName(binding.etFirstName) && Validator.isValidName(binding.etLastName) && Validator.isValidCountryName(
            binding.etCountry
        ) &&
                Validator.isValidStreetName(binding.etStreet) && Validator.isValidCityName(binding.etStreet) && Validator.isValidCityName(
            binding.etCity
        ) &&
                Validator.isValidStreetCode(binding.etStreetCode) && Validator.isValidPostCode(
            binding.etPostalCode
        ) && isDatePicked)
    }

    private fun showSuccess(text: String) {
        showDialog(activity = requireActivity(), body = text) {
            accountCreatedListener.value = user
            findNavController().popBackStack()
        }
    }

    private fun showError(text: String) {
        showNegativeDialog(activity = requireActivity(), body = text) {}
    }

    private fun showLoading(toShow: Boolean) {
        if (toShow) binding.progress.show() else binding.progress.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val accountCreatedListener = SingleLiveEvent<UserModel>()
    }

}