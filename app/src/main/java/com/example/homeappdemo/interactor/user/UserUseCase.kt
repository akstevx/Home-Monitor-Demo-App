package com.example.homeappdemo.interactor.user

import androidx.lifecycle.LiveData
import com.example.homeappdemo.datasource.repository.local.UserRepository
import com.example.homeappdemo.model.user.UserModel
import javax.inject.Inject


interface UserUseCase {
    fun getUser(): LiveData<UserModel>?
    suspend fun createUser(user: UserModel)
    suspend fun updateUser(user: UserModel)
    suspend fun deleteUser()
}

class UserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UserUseCase {
    override fun getUser(): LiveData<UserModel>? {
        return userRepository.getUser()
    }

    override suspend fun createUser(user: UserModel) {
        userRepository.createUser(user)
    }

    override suspend fun updateUser(user: UserModel) {
        userRepository.updateUser(user)
    }

    override suspend fun deleteUser() {
        userRepository.deleteUser()
    }
}