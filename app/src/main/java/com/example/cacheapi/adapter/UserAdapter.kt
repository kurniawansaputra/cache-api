package com.example.cacheapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cacheapi.databinding.ItemRowUserBinding
import com.example.cacheapi.model.User

class UserAdapter(private val userList: List<User>, private val context: Context): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(userList[position]) {
                val id = id
                val username = username
                val avatarUrl = avatarUrl

                binding.apply {
                    textUsername.text = username
                    Glide.with(context)
                        .load(avatarUrl)
                        .centerCrop()
                        .into(ivAvatar)
                }
            }
        }
    }

    override fun getItemCount(): Int = userList.size
}