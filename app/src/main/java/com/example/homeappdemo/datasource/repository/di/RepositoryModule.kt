package com.example.homeappdemo.datasource.repository.di

import com.example.homeappdemo.datasource.local.dao.DeviceDao
import com.example.homeappdemo.datasource.local.dao.UserDao
import com.example.homeappdemo.datasource.repository.local.DeviceRepository
import com.example.homeappdemo.datasource.repository.local.DeviceRepositoryImpl
import com.example.homeappdemo.datasource.repository.local.UserRepository
import com.example.homeappdemo.datasource.repository.local.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao):
            UserRepository = UserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideDeviceRepository(deviceDao: DeviceDao):
            DeviceRepository = DeviceRepositoryImpl(deviceDao)

}