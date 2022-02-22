package com.example.homeappdemo.ui.fragments.device

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.FragmentDeviceBinding
import com.example.homeappdemo.model.device.DeviceModel
import com.example.homeappdemo.ui.fragments.device.adaptor.DeviceAdaptor
import com.example.homeappdemo.ui.general.showNegativeDialog
import com.example.homeappdemo.util.AdaptorCallback
import com.example.homeappdemo.util.SingleLiveEvent
import com.example.homeappdemo.util.extensions.hide
import com.example.homeappdemo.util.extensions.show
import com.example.homeappdemo.util.observeChange


class DeviceFragment : Fragment() {
    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeviceViewModel by activityViewModels()
    private val adaptor: DeviceAdaptor by lazy { DeviceAdaptor(adaptorCallback, mutableListOf()) }
    private val adaptorCallback: AdaptorCallback<DeviceModel> = object :
        AdaptorCallback<DeviceModel> {
        override fun onClicked(item: DeviceModel) {
            val action = DeviceFragmentDirections.navigateToEditDeviceFragment(device = item)
            findNavController().navigate(action)
        }
    }
    private var sortListener = SingleLiveEvent<String>()
    private val light = "Light"
    private val heater = "Heater"
    private val rollerShutter = "RollerShutter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        setUpActionBar()
        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }


    private fun setUpActionBar() {
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        (activity as AppCompatActivity?)?.supportActionBar?.title =
            getString(R.string.app_demo_name)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(false)
    }

    private fun updateUI() {
        loadViewState()
        setClickListeners()
    }

    private fun loadViewState() {
        if (viewModel.getDeviceStatus()) {
            binding.groupEmptyState.hide()
            binding.group.show()
            displayDevices()
        } else {
            binding.groupEmptyState.show()
            binding.group.hide()
        }
    }

    private fun displayDevices() {
        viewModel.getAllDevices().observeChange(viewLifecycleOwner) {
            adaptor.onNewData(it)
            setUpRecycler()
        }
    }

    private fun displayDevicesByProduct(productType: String) {
        viewModel.getDeviceByProduct(productType).observeChange(viewLifecycleOwner) {
            adaptor.onNewData(it)
            setUpRecycler()
        }
    }

    private fun setClickListeners() {
        binding.btnAddNewDevice.setOnClickListener {
            if (viewModel.getAccountStatus()) {
                findNavController().navigate(R.id.navigateToChooseProductTypeDialogFragment)
            } else showError(getString(R.string.create_account_device))
        }

        binding.btnAddNewDevice2.setOnClickListener {
            findNavController().navigate(R.id.navigateToChooseProductTypeDialogFragment)
        }
    }

    private fun setUpRecycler() {
        binding.recyclerDevices.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerDevices.adapter = adaptor
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_light -> {
                displayDevicesByProduct(light)
            }
            R.id.sort_by_heater -> {
                displayDevicesByProduct(heater)
            }
            R.id.sort_by_shutter -> {
                displayDevicesByProduct(rollerShutter)
            }
            R.id.menu_show_all -> {
                displayDevices()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showError(text: String) {
        showNegativeDialog(activity = requireActivity(), body = text) {}
    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {

    }
}