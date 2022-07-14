package com.example.cacheapi.repository

import com.example.cacheapi.database.UserDao
import com.example.cacheapi.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository (private val userDao: UserDao){
    val users = userDao.loadUsers()

    suspend fun refreshUser() {
        withContext(Dispatchers.IO) {
            val users = ApiConfig.getApiService().getListUser()
            userDao.insertAll(users)
        }
    }
}