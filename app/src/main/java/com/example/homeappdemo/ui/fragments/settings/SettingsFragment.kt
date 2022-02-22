package com.example.homeappdemo.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.FragmentSettingsBinding
import com.example.homeappdemo.ui.MainActivity
import com.example.homeappdemo.ui.fragments.settings.adaptor.SettingsAdaptor
import com.example.homeappdemo.ui.general.showDialog
import com.example.homeappdemo.ui.general.showDialogWithNegative
import com.example.homeappdemo.ui.general.showLanguageDialog
import com.example.homeappdemo.ui.general.showNegativeDialog
import com.example.homeappdemo.util.AdaptorCallback
import com.example.homeappdemo.util.SingleLiveEvent
import com.example.homeappdemo.util.extensions.hide
import com.example.homeappdemo.util.extensions.show
import com.example.homeappdemo.util.observeChange

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by activityViewModels()

    private val adaptor: SettingsAdaptor by lazy {
        SettingsAdaptor(
            adaptorCallback,
            requireContext()
        )
    }
    private val adaptorCallback: AdaptorCallback<SettingsAdaptor.SettingsItem> = object :
        AdaptorCallback<SettingsAdaptor.SettingsItem> {
        override fun onClicked(item: SettingsAdaptor.SettingsItem) {
            when (item.itemPosition) {
                0 -> deleteAccount()
                1 -> clearDevices()
                2 -> clearAppData()
                3 -> changeLanguage()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        showLoading(false)
        setUpRecycler()
        setObservers()
    }

    private fun setObservers() {
        viewModel.showLoading.observeChange(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.clearAccountListener.observeChange(viewLifecycleOwner) {
            setUpDialog(getString(R.string.account_deleted))
        }

        viewModel.clearDevicesListener.observeChange(viewLifecycleOwner) {
            setUpDialog(getString(R.string.devices_deleted))
        }

        viewModel.clearAppListener.observeChange(viewLifecycleOwner) {
            setUpDialog(getString(R.string.app_reset))
        }
    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adaptor
    }

    private fun deleteAccount() {
        if (viewModel.getAccountStatus()) {
            showWarning(getString(R.string.are_you_sure_account)) {
                viewModel.deleteUserAccount()
            }
        } else showError(getString(R.string.no_account))
    }

    private fun clearDevices() {
        if (viewModel.getDeviceStatus()) {
            showWarning(getString(R.string.are_you_sure_device)) {
                viewModel.clearDevices()
            }
        } else showError(getString(R.string.no_device))
    }

    private fun clearAppData() {
        showWarning(getString(R.string.are_you_sure)) {
            viewModel.clearAppData()
        }
    }

    private fun changeLanguage() {
        showLanguageDialog(
            activity = requireActivity(),
            body = getString(R.string.are_you_language),
            frenchAction = {
                (activity as MainActivity).changeLanguage(
                    context = requireContext(),
                    locale = MainActivity.Language.FR
                )
            },
            englishAction = {
                (activity as MainActivity).changeLanguage(
                    context = requireContext(),
                    locale = MainActivity.Language.EN
                )
            }
        )
    }

    private fun setUpDialog(text: String) {
        showDialog(activity = requireActivity(), body = text) {
            resetDataListener.value = ""
            findNavController().popBackStack()
        }
    }

    private fun showError(text: String) {
        showNegativeDialog(activity = requireActivity(), body = text) {}
    }

    private fun showWarning(text: String, action: () -> Unit) {
        showDialogWithNegative(activity = requireActivity(), body = text) {
            action()
        }
    }

    private fun showLoading(toShow: Boolean) {
        if (toShow) binding.progress.show() else binding.progress.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val resetDataListener = SingleLiveEvent<String>()
    }
}