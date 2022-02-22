package com.example.homeappdemo.datasource.repository.local

import androidx.lifecycle.LiveData
import com.example.homeappdemo.datasource.local.dao.UserDao
import com.example.homeappdemo.model.user.UserModel

interface UserRepository {
    suspend fun createUser(user: UserModel)
    fun getUser(): LiveData<UserModel>?
    suspend fun deleteUser()
    suspend fun updateUser(user: UserModel)
}

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun createUser(user: UserModel) {
        userDao.createUser(user)
    }

    override fun getUser(): LiveData<UserModel>? {
        return userDao.getUser()
    }

    override suspend fun deleteUser() {
        return userDao.deleteUser()
    }

    override suspend fun updateUser(user: UserModel) {
        return userDao.updateUser(user)
    }

}
