package com.example.homeappdemo.datasource.repository.remote

import com.example.homeappdemo.datasource.network.ApiService
import com.example.homeappdemo.model.SampleJSon
import com.example.homeappdemo.util.exceptions.NetworkException
import java.lang.Exception
import javax.inject.Inject

interface RemoteRepository {
    suspend fun getSampleJson(): SampleJSon
}

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RemoteRepository {

    override suspend fun getSampleJson(): SampleJSon {
        return try {
            apiService.getJsonSample().await()
        } catch (ex: Exception) {
            throw NetworkException(ex.toString())
        }
    }
}