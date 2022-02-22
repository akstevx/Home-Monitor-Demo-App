package com.example.homeappdemo.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homeappdemo.datasource.local.dao.DeviceDao
import com.example.homeappdemo.datasource.local.dao.UserDao
import com.example.homeappdemo.model.device.DeviceModel
import com.example.homeappdemo.model.user.UserModel

@Database(entities = [UserModel::class, DeviceModel::class], version = 4, exportSchema = false)
@TypeConverters(AddressConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deviceDao(): DeviceDao
}