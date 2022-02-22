package com.example.homeappdemo.datasource.local.di

import android.content.Context
import androidx.room.Room
import com.example.homeappdemo.datasource.local.dao.DeviceDao
import com.example.homeappdemo.datasource.local.dao.UserDao
import com.example.homeappdemo.datasource.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "AppDatabase"

    @Provides
    fun provideDatabaseName(): String {
        return DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, provideDatabaseName())
            .fallbackToDestructiveMigration()
            .build()
    }

    //DAO
    @Provides
    @Singleton
    fun provideDeviceDao(appDatabase: AppDatabase): DeviceDao {
        return appDatabase.deviceDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

}