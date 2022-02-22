package com.example.homeappdemo.model.device

data class LightDevice(
    val id: Int,
    val deviceName: String,
    val intensity: Int,
    val mode: String,
    val productType: String
)