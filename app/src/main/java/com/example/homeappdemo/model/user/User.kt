package com.example.homeappdemo.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey val id: Int = 1,
    val address: Address,
    val birthDate: String,
    val firstName: String,
    val lastName: String
)