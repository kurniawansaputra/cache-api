package com.example.cacheapi.repository

import android.app.Application
import com.example.cacheapi.database.UserDao
import com.example.cacheapi.model.User
import com.example.cacheapi.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainRepository (private val userDao: UserDao){
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val users = userDao.loadUsers()

    suspend fun refreshUser() {
        withContext(Dispatchers.IO) {
            val users = ApiConfig.getApiService().getListUser()
            userDao.insertAll(users)
        }
    }

    fun deleteUser(user: User) {
        executorService.execute { userDao.deleteUser(user) }
    }
}