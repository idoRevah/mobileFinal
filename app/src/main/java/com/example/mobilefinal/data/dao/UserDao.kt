package com.example.mobilefinal.data.dao
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.mobilefinal.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    suspend fun getUserByUid(id: String): User?

    @Query("SELECT id FROM users WHERE id IN (:ids)")
    suspend fun getExistingUserIds(ids: List<String>): List<String>

    @Upsert
    fun upsertAll(vararg users: User)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteByUid(id: String)

    @Update
    fun updateUserData(user: User)
}