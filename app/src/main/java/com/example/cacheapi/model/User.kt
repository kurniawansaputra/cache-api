package com.example.cacheapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(

	@field:SerializedName("avatar_url")
	var avatarUrl: String? = null,

	@field:SerializedName("id")
	@PrimaryKey var id: Int? = null,

	@field:SerializedName("username")
	var username: String? = null
)
