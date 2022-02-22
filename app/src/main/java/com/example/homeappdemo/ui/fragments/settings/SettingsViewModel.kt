package com.example.homeappdemo.ui.fragments.settings

import androidx.lifecycle.viewModelScope
import com.example.homeappdemo.datasource.local.database.AppDatabase
import com.example.homeappdemo.interactor.device.DeviceUseCase
import com.example.homeappdemo.interactor.user.UserUseCase
import com.example.homeappdemo.sharedpreference.*
import com.example.homeappdemo.ui.BaseViewModel
import com.example.homeappdemo.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val deviceUseCase: DeviceUseCase,
    private val localStorage: LocalStorage,
    private val appDatabase: AppDatabase
) : BaseViewModel() {
    var showLoading = SingleLiveEvent<Boolean>()
    var clearDevicesListener = SingleLiveEvent<String>()
    var clearAccountListener = SingleLiveEvent<String>()
    var clearAppListener = SingleLiveEvent<String>()

    fun getAccountStatus(): Boolean {
        return localStorage.checkAccountStatus()
    }

    fun getDeviceStatus(): Boolean {
        return localStorage.checkDeviceStatus()
    }

    fun clearDevices() {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                deviceUseCase.deleteAllDevices()
                localStorage.setDeviceStatus(false)
                showLoading.postValue(false)
                clearDevicesListener.postValue("")
            }
        }
    }

    fun deleteUserAccount() {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.deleteUser()
                deviceUseCase.deleteAllDevices()
                localStorage.setAccountStatus(false)
                localStorage.setDeviceStatus(false)
                showLoading.postValue(false)
                clearAccountListener.postValue("")
            }
        }
    }

    fun clearAppData() {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                appDatabase.clearAllTables()
                localStorage.setAccountStatus(false)
                localStorage.setDeviceStatus(false)
                showLoading.postValue(false)
                clearAppListener.postValue("")
            }
        }
    }

}