package com.example.homeappdemo.model

import com.example.homeappdemo.model.device.DeviceModel
import com.example.homeappdemo.model.user.UserModel

data class SampleJSon(
    val devices: List<DeviceModel>,
    val user: UserModel
)