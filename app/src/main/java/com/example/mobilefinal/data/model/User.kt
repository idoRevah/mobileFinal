package com.example.mobilefinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String, // Firebase user ID
    val username: String? = null,
    val email: String? = null,
    val profilePictureUrl: String? = null,
    // val bio: String? = null
    // Add other user-related fields here
)