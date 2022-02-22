package com.example.homeappdemo.ui.fragments.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.FragmentEditDeviceBinding
import com.example.homeappdemo.model.device.*
import com.example.homeappdemo.ui.general.showDialog
import com.example.homeappdemo.ui.general.showNegativeDialog
import com.example.homeappdemo.util.Validator
import com.example.homeappdemo.util.extensions.capitalizeWords
import com.example.homeappdemo.util.extensions.hide
import com.example.homeappdemo.util.extensions.show
import com.example.homeappdemo.util.observeChange
import com.king.view.arcseekbar.ArcSeekBar
import hearsilent.discreteslider.DiscreteSlider

class EditDeviceFragment : Fragment() {
    private var _binding: FragmentEditDeviceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DeviceViewModel by activityViewModels()
    private val args: EditDeviceFragmentArgs by navArgs()
    private val device: DeviceModel by lazy { args.device }
    private var intensity: Int = 0
    private var position: Int = 0
    private var temperature: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditDeviceBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        loadViewState(args.device.getProductTypeCase())
        setObservers()
    }

    private fun setObservers() {
        viewModel.createDeviceListener.observeChange(viewLifecycleOwner) {
            showSuccess("${it.deviceName} has successfully been updated")
        }

        viewModel.showLoading.observeChange(viewLifecycleOwner) {
            showLoading(it)
        }
    }


    private fun loadViewState(productType: ProductType) {
        when (productType) {
            ProductType.LIGHTS -> setLightProductTypeView()
            ProductType.ROLLER_SHUTTER -> setShutterProductTypeView()
            ProductType.HEATER -> setHeaterProductTypeView()
            ProductType.NULL -> {
            }
        }
    }

    private fun setLightProductTypeView() {
        binding.lightDeviceLayout.lightContainer.show()
        binding.heaterDeviceLayout.heaterContainer.hide()
        binding.rollerShutterDeviceLayout.rollerContainer.hide()
        updateLightView()
    }

    private fun setHeaterProductTypeView() {
        binding.heaterDeviceLayout.heaterContainer.show()
        binding.lightDeviceLayout.lightContainer.hide()
        binding.rollerShutterDeviceLayout.rollerContainer.hide()
        updateHeaterView()
    }

    private fun setShutterProductTypeView() {
        binding.rollerShutterDeviceLayout.rollerContainer.show()
        binding.heaterDeviceLayout.heaterContainer.hide()
        binding.lightDeviceLayout.lightContainer.hide()
        updateShutterView()
    }

    private fun updateLightView() {
        binding.lightDeviceLayout?.arcSeekBar?.showAnimation(0, device.intensity ?: 0, 3000)
        binding.lightDeviceLayout.txtIntensityLevel.text = device.intensity.toString()
        binding.lightDeviceLayout.statusLightSwitch.isChecked = device.mode == "ON"
        intensity = device.intensity ?: 0
        binding.lightDeviceLayout.etLightName.setText(device.deviceName)
        binding.lightDeviceLayout.btnCreateLightDevice.text = getString(R.string.update_device)

        val onChangeListener: ArcSeekBar.OnChangeListener = object : ArcSeekBar.OnChangeListener {

            override fun onStartTrackingTouch(isCanDrag: Boolean) {
                println(isCanDrag)
            }

            override fun onProgressChanged(progress: Float, max: Float, fromUser: Boolean) {
                intensity = progress.toInt()
                binding.lightDeviceLayout.txtIntensityLevel.text = progress.toString()
            }

            override fun onStopTrackingTouch(isCanDrag: Boolean) {
                println(isCanDrag)
            }

            override fun onSingleTapUp() {
                println("Tap")
            }

        }

        binding.lightDeviceLayout.arcSeekBar.setOnChangeListener(onChangeListener)

        binding.lightDeviceLayout.btnCreateLightDevice.setOnClickListener {
            if (Validator.isValidDeviceName(binding.lightDeviceLayout.etLightName)) {
                editLightDevice()
            }
        }
    }

    private fun updateHeaterView() {
        binding.heaterDeviceLayout.heaterSlider.progress = device.temperature?.toInt() ?: 0
        binding.heaterDeviceLayout.txtHeaterLevel.text = device.temperature.toString()
        binding.heaterDeviceLayout.statusHeaterSwitch.isChecked = device.mode == "ON"
        temperature = device.temperature?.toInt() ?: 0
        binding.heaterDeviceLayout.etHeaterName.setText(device.deviceName)
        binding.heaterDeviceLayout.btnCreateHeater.text = getString(R.string.update_device)

        val listener: DiscreteSlider.OnValueChangedListener =
            object : DiscreteSlider.OnValueChangedListener() {
                override fun onValueChanged(progress: Int, fromUser: Boolean) {
                    super.onValueChanged(progress, fromUser)
                    temperature = progress
                    binding.heaterDeviceLayout.txtHeaterLevel.text = "$progress°"
                }
            }

        binding.heaterDeviceLayout.heaterSlider.setOnValueChangedListener(listener)

        binding.heaterDeviceLayout.btnCreateHeater.setOnClickListener {
            if (Validator.isValidDeviceName(binding.heaterDeviceLayout.etHeaterName)) {
                editHeaterDevice()
            }
        }
    }

    private fun updateShutterView() {
        binding.rollerShutterDeviceLayout.shutterSlider.progress = device.position ?: 0
        binding.rollerShutterDeviceLayout.txtShutterLevel.text = device.position.toString()
        binding.rollerShutterDeviceLayout.etShutterName.setText(device.deviceName)
        binding.rollerShutterDeviceLayout.btnCreateShutter.text = getString(R.string.update_device)
        position = device.position ?: 0

        val listener: DiscreteSlider.OnValueChangedListener =
            object : DiscreteSlider.OnValueChangedListener() {
                override fun onValueChanged(progress: Int, fromUser: Boolean) {
                    super.onValueChanged(progress, fromUser)
                    position = progress
                    binding.rollerShutterDeviceLayout.txtShutterLevel.text = progress.toString()
                }
            }

        binding.rollerShutterDeviceLayout.shutterSlider.setOnValueChangedListener(listener)

        binding.rollerShutterDeviceLayout.btnCreateShutter.setOnClickListener {
            if (Validator.isValidDeviceName(binding.rollerShutterDeviceLayout.etShutterName)) {
                editShutterDevice()
            }
        }
    }

    private fun editLightDevice() {
        val mode = if (binding.lightDeviceLayout.statusLightSwitch.isChecked) "ON" else "OFF"
        val deviceName = binding.lightDeviceLayout.etLightName.text.toString().capitalizeWords()

        val device: DeviceModel =
            buildLightDevice(mode = mode, deviceName = deviceName, intensity = intensity)
        viewModel.updateDevice(device, this.device.id)
    }

    private fun editHeaterDevice() {
        val mode = if (binding.heaterDeviceLayout.statusHeaterSwitch.isChecked) "ON" else "OFF"
        val deviceName = binding.heaterDeviceLayout.etHeaterName.text.toString().capitalizeWords()

        if (viewModel.isValidTemperature(temperature)) {
            val device: DeviceModel =
                buildHeaterDevice(
                    mode = mode,
                    deviceName = deviceName,
                    temperature = temperature
                )
            viewModel.updateDevice(device, this.device.id)
        } else showError(getString(R.string.temperature_must_not))
    }

    private fun editShutterDevice() {
        val deviceName =
            binding.rollerShutterDeviceLayout.etShutterName.text.toString().capitalizeWords()

        val device: DeviceModel =
            buildShutterDevice(deviceName = deviceName, position = position)
        viewModel.updateDevice(device, this.device.id)
    }


    private fun showSuccess(text: String) {
        showDialog(activity = requireActivity(), body = text) {
            findNavController().popBackStack()
        }
    }

    private fun showError(text: String) {
        showNegativeDialog(activity = requireActivity(), body = text) {}
    }

    private fun showLoading(toShow: Boolean) {
        if (toShow) binding.progress.show() else binding.progress.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}