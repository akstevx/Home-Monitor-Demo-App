package com.example.homeappdemo.interactor.device

import com.example.homeappdemo.datasource.repository.local.DeviceRepository
import com.example.homeappdemo.model.device.DeviceModel
import kotlinx.coroutines.flow.Flow

interface DeviceUseCase {
    fun getAllDevices(): Flow<List<DeviceModel>>
    fun getDeviceCount(): Int
    fun getDeviceByProduct(productType: String): Flow<List<DeviceModel>>
    fun getDeviceCountByProduct(productType: String): Int
    suspend fun createDevice(device: DeviceModel)
    suspend fun updateDevice(device: DeviceModel, currentDeviceId: Int)
    suspend fun deleteDevice(device: DeviceModel)
    suspend fun deleteAllDevices()
}

class DeviceUseCaseImpl(private val deviceRepository: DeviceRepository) : DeviceUseCase {

    override fun getAllDevices(): Flow<List<DeviceModel>> {
        return deviceRepository.getAllDevices()
    }

    override fun getDeviceCount(): Int {
        return deviceRepository.getDeviceCount()
    }

    override fun getDeviceByProduct(productType: String): Flow<List<DeviceModel>> {
        return deviceRepository.getDeviceByProduct(productType)
    }

    override fun getDeviceCountByProduct(productType: String): Int {
        return deviceRepository.getDeviceCountByProduct(productType)
    }

    override suspend fun createDevice(device: DeviceModel) {
        deviceRepository.createDevice(device)
    }

    override suspend fun updateDevice(device: DeviceModel, currentDeviceId: Int) {
        deviceRepository.updateDevice(device, currentDeviceId)
    }

    override suspend fun deleteDevice(device: DeviceModel) {
        deviceRepository.deleteDevice(device.id)
    }

    override suspend fun deleteAllDevices() {
        deviceRepository.deleteAllDevices()
    }

}
