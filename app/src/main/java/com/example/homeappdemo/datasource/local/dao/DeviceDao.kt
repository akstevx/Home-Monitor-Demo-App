package com.example.homeappdemo.datasource.local.dao

import androidx.room.*
import com.example.homeappdemo.model.device.DeviceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createDevice(toDoData: DeviceModel)

    @Query("DELETE FROM device WHERE id == :id")
    suspend fun deleteDevice(id: Int)

    @Query("DELETE FROM device")
    suspend fun deleteAllDevices()

    @Query("SELECT * FROM device")
    fun getAllDevices(): Flow<List<DeviceModel>>

    @Query("SELECT COUNT(*) FROM device")
    fun getDeviceCount(): Int

    @Query("SELECT COUNT(*) FROM device WHERE productType == :productType")
    fun getDeviceCountByProduct(productType: String): Int

    @Query("SELECT * FROM device WHERE productType == :productType")
    fun getDeviceByProduct(productType: String): Flow<List<DeviceModel>>

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDevice(device: DeviceModel, currentDeviceId: Int) {
        deleteDevice(currentDeviceId)
        createDevice(device)
    }

}