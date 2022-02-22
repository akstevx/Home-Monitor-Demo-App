package com.example.homeappdemo.ui.di

import com.example.homeappdemo.datasource.local.database.AppDatabase
import com.example.homeappdemo.datasource.repository.local.DeviceRepository
import com.example.homeappdemo.interactor.device.DeviceUseCase
import com.example.homeappdemo.interactor.user.UserUseCase
import com.example.homeappdemo.sharedpreference.LocalStorage
import com.example.homeappdemo.ui.fragments.device.DeviceViewModel
import com.example.homeappdemo.ui.fragments.profile.UserViewModel
import com.example.homeappdemo.ui.fragments.settings.SettingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun provideDeviceViewModel(
        deviceUseCase: DeviceUseCase,
        localStorage: LocalStorage
    ): DeviceViewModel =
        DeviceViewModel(deviceUseCase, localStorage)

    @Provides
    @Singleton
    fun provideUserViewModel(
        userUseCase: UserUseCase,
        deviceUseCase: DeviceUseCase,
        localStorage: LocalStorage
    ): UserViewModel =
        UserViewModel(userUseCase, deviceUseCase, localStorage)

    @Provides
    @Singleton
    fun provideSettingsViewModel(
        userUseCase: UserUseCase,
        deviceUseCase: DeviceUseCase,
        localStorage: LocalStorage,
        appDatabase: AppDatabase
    ): SettingsViewModel =
        SettingsViewModel(userUseCase, deviceUseCase, localStorage, appDatabase)

}