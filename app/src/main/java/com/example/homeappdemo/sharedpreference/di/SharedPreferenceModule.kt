package com.example.homeappdemo.sharedpreference.di

import android.content.Context
import com.example.homeappdemo.sharedpreference.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideLocalStorage(context: Context): LocalStorage {
        return LocalStorage(context = context )
    }

}