package com.example.homeappdemo.interactor.di

import com.example.homeappdemo.datasource.repository.local.DeviceRepository
import com.example.homeappdemo.datasource.repository.local.UserRepository
import com.example.homeappdemo.interactor.device.DeviceUseCase
import com.example.homeappdemo.interactor.device.DeviceUseCaseImpl
import com.example.homeappdemo.interactor.user.UserUseCase
import com.example.homeappdemo.interactor.user.UserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository):
            UserUseCase = UserUseCaseImpl(userRepository)

    @Provides
    @Singleton
    fun provideDeviceUseCase(deviceRepository: DeviceRepository):
            DeviceUseCase = DeviceUseCaseImpl(deviceRepository = deviceRepository)
}