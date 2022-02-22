package com.example.homeappdemo.model.device

data class HeaterDevice(
    val id: Int,
    val deviceName: String,
    val mode: String,
    val temperature: String,
    val productType: String
)