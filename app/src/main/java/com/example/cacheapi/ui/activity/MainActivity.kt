package com.example.cacheapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.cacheapi.adapter.UserAdapter
import com.example.cacheapi.databinding.ActivityMainBinding
import com.example.cacheapi.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setListUser()

    }

    private fun setListUser() {
        mainViewModel.isLoading.observe(this) {
            setLoading(it)
        }
        mainViewModel.user.observe(this) {
            val userAdapter = UserAdapter(it, this)
            binding.rvUser.adapter = userAdapter
            binding.rvUser.setHasFixedSize(true)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }
}