package com.example.homeappdemo.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.homeappdemo.interactor.device.DeviceUseCase
import com.example.homeappdemo.interactor.user.UserUseCase
import com.example.homeappdemo.model.user.Address
import com.example.homeappdemo.model.user.UserModel
import com.example.homeappdemo.sharedpreference.LocalStorage
import com.example.homeappdemo.sharedpreference.checkAccountStatus
import com.example.homeappdemo.sharedpreference.setAccountStatus
import com.example.homeappdemo.ui.BaseViewModel
import com.example.homeappdemo.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val deviceUseCase: DeviceUseCase,
    private val localStorage: LocalStorage
) : BaseViewModel() {
    var showLoading = SingleLiveEvent<Boolean>()
    var createAccountListener = SingleLiveEvent<String>()
    var updateAccountListener = SingleLiveEvent<String>()
    var deviceCount = 0
    var resetDeviceCount = SingleLiveEvent<String>()
    var lightCount = 0
    var rollerShutterCount = 0
    var heaterCount = 0
    var resetDeviceCountByProduct = SingleLiveEvent<String>()
    var user: UserModel? = null


    fun getAccountStatus(): Boolean {
        return localStorage.checkAccountStatus()
    }

    fun getUser(): LiveData<UserModel>? = userUseCase.getUser()

    fun createAddress(
        city: String,
        postalCode: String,
        street: String,
        streetCode: String,
        country: String
    ): Address {
        return Address(
            city = city,
            postalCode = postalCode.toInt(),
            street = street,
            streetCode = streetCode,
            country = country
        )
    }

    fun createUser(user: UserModel) {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.createUser(user)
                localStorage.setAccountStatus(true)
                showLoading.postValue(false)
                createAccountListener.postValue("")
            }
        }
    }

    fun updateUser(user: UserModel) {
        showLoading.value = true
        delayFor(3000) {
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.updateUser(user)
                showLoading.postValue(false)
                updateAccountListener.postValue("")
            }
        }
    }

    fun getNumberOfDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            deviceCount =
                withContext(Dispatchers.Default) { deviceUseCase.getDeviceCount() }
            resetDeviceCount.postValue("RESET")
        }
    }

    fun getNumberOfDeviceByProductType() {
        viewModelScope.launch(Dispatchers.IO) {
            lightCount = withContext(Dispatchers.Default) {
                deviceUseCase.getDeviceCountByProduct("Light")
            }
            heaterCount = withContext(Dispatchers.Default) {
                deviceUseCase.getDeviceCountByProduct("Heater")
            }
            rollerShutterCount = withContext(Dispatchers.Default) {
                deviceUseCase.getDeviceCountByProduct("RollerShutter")
            }
            resetDeviceCountByProduct.postValue("RESET")
        }

    }
}