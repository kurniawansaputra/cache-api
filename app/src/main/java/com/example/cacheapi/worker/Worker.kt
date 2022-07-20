package com.example.cacheapi.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cacheapi.database.UserRoomDatabase
import com.example.cacheapi.repository.MainRepository
import retrofit2.HttpException

class Worker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val userDao = UserRoomDatabase.getDatabase(applicationContext).userDao()
        val repository = MainRepository(userDao)

        return try {
            repository.refreshUser()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}