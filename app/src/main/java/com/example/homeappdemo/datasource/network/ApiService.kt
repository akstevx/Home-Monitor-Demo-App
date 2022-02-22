package com.example.homeappdemo.datasource.network

import com.example.homeappdemo.model.SampleJSon
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("modulotest/data.json")
    fun getJsonSample(): Deferred<SampleJSon>
}