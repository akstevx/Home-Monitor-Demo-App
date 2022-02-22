package com.example.homeappdemo.model.device

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "device")
@Parcelize
data class DeviceModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val deviceName: @RawValue String? = null,
    val intensity: @RawValue Int? = null,
    val mode: @RawValue String? = null,
    val position: @RawValue Int? = null,
    val productType: String,
    val temperature: @RawValue String? = null
) : Parcelable {
    fun getProductTypeCase(): ProductType {
        return when (productType) {
            "Heater" -> ProductType.HEATER
            "RollerShutter" -> ProductType.ROLLER_SHUTTER
            "Light" -> ProductType.LIGHTS
            else -> ProductType.NULL
        }
    }
}

fun buildLightDevice(mode: String, intensity: Int, deviceName: String): DeviceModel {
    return DeviceModel(
        productType = "Light",
        id = 0,
        mode = mode,
        intensity = intensity,
        deviceName = deviceName
    )
}

fun buildHeaterDevice(mode: String, temperature: Int, deviceName: String): DeviceModel {
    return DeviceModel(
        productType = "Heater",
        id = 0,
        mode = mode,
        temperature = temperature.toString(),
        deviceName = deviceName
    )
}


fun buildShutterDevice(position: Int, deviceName: String): DeviceModel {
    return DeviceModel(
        productType = "RollerShutter",
        id = 0,
        position = position,
        deviceName = deviceName
    )
}

enum class ProductType {
    LIGHTS, ROLLER_SHUTTER, HEATER, NULL
}