package com.example.cacheapi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cacheapi.database.UserDao
import com.example.cacheapi.database.UserRoomDatabase
import com.example.cacheapi.model.User
import com.example.cacheapi.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var repository: MainRepository
    private lateinit var userDao: UserDao

    private var job = Job()
    private var crScope = CoroutineScope(job + Dispatchers.Main)

    private lateinit var _user : LiveData<List<User>>
    val user: LiveData<List<User>>
    get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
    get() = _isLoading

    fun getUser() {
        userDao = UserRoomDatabase.getDatabase(getApplication()).userDao()
        repository = MainRepository(userDao)

        _isLoading.value = true
        crScope.launch {
            try {
                repository.refreshUser()
            } catch (t: Throwable) {
                _isLoading.value = false
            }
        }

        _user = repository.users
        _isLoading.value = false
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}