package com.example.cacheapi.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.cacheapi.adapter.UserAdapter
import com.example.cacheapi.databinding.ActivityMainBinding
import com.example.cacheapi.model.User
import com.example.cacheapi.viewmodel.MainViewModel
import com.example.cacheapi.worker.Worker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateInit(this)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setListUser()

    }

    private fun lateInit(context: Context) {
        CoroutineScope(Dispatchers.Default).launch {
            val constraint = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val request = PeriodicWorkRequestBuilder<Worker>(
                1, TimeUnit.DAYS)
                .setConstraints(constraint)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "refresh data user",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }

    private fun setListUser() {
        mainViewModel.isLoading.observe(this) {
            setLoading(it)
        }
        mainViewModel.getUser()
        mainViewModel.user.observe(this) {
            val userAdapter = UserAdapter(it, this)
            binding.rvUser.adapter = userAdapter
            binding.rvUser.setHasFixedSize(true)
        }
    }

    fun onItemClicked(user: User, position: Int) {

    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }
}