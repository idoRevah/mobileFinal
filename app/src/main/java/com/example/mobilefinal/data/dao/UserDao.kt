package com.example.mobilefinal.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobilefinal.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE userId = :uid")
    fun getUser(uid: String): LiveData<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}