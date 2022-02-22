package com.example.homeappdemo.ui.fragments.device

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.homeappdemo.interactor.device.DeviceUseCase
import com.example.homeappdemo.model.device.DeviceModel
import com.example.homeappdemo.model.device.ProductType
import com.example.homeappdemo.sharedpreference.LocalStorage
import com.example.homeappdemo.sharedpreference.checkAccountStatus
import com.example.homeappdemo.sharedpreference.checkDeviceStatus
import com.example.homeappdemo.sharedpreference.setDeviceStatus
import com.example.homeappdemo.ui.BaseViewModel
import com.example.homeappdemo.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val deviceUseCase: DeviceUseCase,
    private val localStorage: LocalStorage
) : BaseViewModel() {
    var showLoading = SingleLiveEvent<Boolean>()
    var deleteDeviceListener = SingleLiveEvent<DeviceModel>()
    var deleteAllDevicesListener = SingleLiveEvent<String>()
    var createDeviceListener = SingleLiveEvent<DeviceModel>()

    fun getDeviceStatus(): Boolean {
        return localStorage.checkDeviceStatus()
    }

    fun getAccountStatus(): Boolean {
        return localStorage.checkAccountStatus()
    }

    fun getAllDevices() = deviceUseCase.getAllDevices().asLiveData()

    fun getDeviceByProduct(productType: String) =
        deviceUseCase.getDeviceByProduct(productType).asLiveData()

    fun createDevice(device: DeviceModel) {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                deviceUseCase.createDevice(device)
                localStorage.setDeviceStatus(true)
                showLoading.postValue(false)
                createDeviceListener.postValue(device)
            }
        }
    }

    fun updateDevice(device: DeviceModel, currentId: Int) {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                deviceUseCase.updateDevice(device, currentId)
                showLoading.postValue(false)
                createDeviceListener.postValue(device)
            }
        }
    }

    fun deleteDevice(device: DeviceModel) {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                deviceUseCase.deleteDevice(device)
                showLoading.postValue(false)
                deleteDeviceListener.postValue(device)
            }
        }
    }

    fun clearDevices() {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                deviceUseCase.deleteAllDevices()
                localStorage.setDeviceStatus(false)
                showLoading.postValue(false)
                deleteAllDevicesListener.postValue("")
            }
        }
    }

    fun getDeviceType(productType: String): ProductType {
        return when (productType) {
            "Heater" -> ProductType.HEATER
            "RollerShutter" -> ProductType.ROLLER_SHUTTER
            "Light" -> ProductType.LIGHTS
            else -> ProductType.NULL
        }
    }

    fun isValidTemperature(temperature: Int): Boolean {
        return temperature >= 7
    }

}