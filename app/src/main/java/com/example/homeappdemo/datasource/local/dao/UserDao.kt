package com.example.homeappdemo.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.homeappdemo.model.user.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id == 1")
    fun getUser(): LiveData<UserModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserModel)

    @Query("DELETE FROM user WHERE id == 1")
    suspend fun deleteUser()

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserModel) {
        deleteUser()
        createUser(user)
    }
}