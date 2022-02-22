package com.example.homeappdemo.datasource.repository.local

import com.example.homeappdemo.datasource.local.dao.DeviceDao
import com.example.homeappdemo.model.device.DeviceModel
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getAllDevices(): Flow<List<DeviceModel>>
    fun getDeviceCount(): Int
    fun getDeviceCountByProduct(productType: String): Int
    fun getDeviceByProduct(productType: String): Flow<List<DeviceModel>>
    suspend fun createDevice(device: DeviceModel)
    suspend fun updateDevice(device: DeviceModel, currentDeviceId: Int)
    suspend fun deleteDevice(id: Int)
    suspend fun deleteAllDevices()
}

class DeviceRepositoryImpl(private val deviceDao: DeviceDao) : DeviceRepository {

    override fun getAllDevices(): Flow<List<DeviceModel>> {
        return deviceDao.getAllDevices()
    }

    override fun getDeviceCount(): Int {
        return deviceDao.getDeviceCount()
    }

    override fun getDeviceCountByProduct(productType: String): Int {
        return deviceDao.getDeviceCountByProduct(productType)
    }

    override fun getDeviceByProduct(productType: String): Flow<List<DeviceModel>> {
        return deviceDao.getDeviceByProduct(productType)
    }

    override suspend fun createDevice(device: DeviceModel) {
        deviceDao.createDevice(device)
    }

    override suspend fun updateDevice(device: DeviceModel, currentDeviceId: Int) {
        deviceDao.updateDevice(device, currentDeviceId)
    }

    override suspend fun deleteDevice(id: Int) {
        deviceDao.deleteDevice(id)
    }

    override suspend fun deleteAllDevices() {
        deviceDao.deleteAllDevices()
    }

}
