package com.example.homeappdemo.ui.fragments.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.FragmentUserProfileBinding
import com.example.homeappdemo.model.user.UserModel
import com.example.homeappdemo.ui.fragments.settings.SettingsFragment
import com.example.homeappdemo.util.extensions.capitalizeWords
import com.example.homeappdemo.util.extensions.hide
import com.example.homeappdemo.util.extensions.show
import com.example.homeappdemo.util.observeChange

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        setObservers()
    }

    private fun setObservers() {
        SettingsFragment.resetDataListener.observeChange(viewLifecycleOwner) {
            updateUI()
        }

        CreateProfileFragment.accountCreatedListener.observeChange(viewLifecycleOwner) {
            updateUI()
        }
    }

    private fun updateUI() {
        loadViewState()
        setClickListeners()
    }

    private fun loadViewState() {
        if (viewModel.getAccountStatus()) {
            binding.container.show()
            binding.emptyContainer.hide()
            displayUser()
        } else {
            binding.container.hide()
            binding.emptyContainer.show()
        }
    }

    private fun displayUser() {
        viewModel.getUser()?.observeChange(viewLifecycleOwner) {
            updateViewsWithAccount(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViewsWithAccount(user: UserModel) {
        getDevices()
        binding.txtAccountName.text = "${user.firstName} ${user.lastName}".capitalizeWords()
        binding.txtAddress.text =
            "${user.address.street}, ${user.address.streetCode}".capitalizeWords()
        binding.txtCity.text = user.address.city.capitalizeWords()
        binding.txtCountry.text = user.address.country.capitalizeWords()
        binding.txtDateOfBirth.text = user.birthDate
    }

    private fun getDevices() {
        println("GET DEVICES CALLED!!")
        viewModel.getNumberOfDevices()
        viewModel.getNumberOfDeviceByProductType()
        observeDeviceTextChange()
    }

    private fun observeDeviceTextChange() {
        viewModel.resetDeviceCount.observeChange(viewLifecycleOwner) {
            println("GET DEVICES OBSERVED!!")

            binding.txtDeviceCount.text = "${viewModel.deviceCount}"
        }

        viewModel.resetDeviceCountByProduct.observeChange(viewLifecycleOwner) {
            println("GET DEVICES BY PRODUCT OBSERVED!!")

            binding.txtRollerShuttersCount.text = "${viewModel.rollerShutterCount}"
            binding.txtLightCount.text = "${viewModel.lightCount}"
            binding.txtHeatersCount.text = "${viewModel.heaterCount}"
        }
    }

    private fun setClickListeners() {
        binding.btnCreateNewUser.setOnClickListener {
            findNavController().navigate(R.id.navigateToCreateProfileFragment)
        }

        binding.btnUpdateUser.setOnClickListener {
            findNavController().navigate(R.id.navigateToCreateProfileFragment)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}