package com.example.cacheapi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cacheapi.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun loadUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun deleteUser(user: User)
}