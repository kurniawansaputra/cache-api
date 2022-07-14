package com.example.cacheapi.network

import com.example.cacheapi.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("test-api/data")
    suspend fun getListUser(
    ): List<User>
}