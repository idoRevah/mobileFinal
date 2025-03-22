package com.example.mobilefinal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey val id: String = "",
    val workoutId: String = "",
    var content: String = "",
    var image: String? = null,
    val authorUserId: String = "",
    val authorNickname: String = "",
    var authorProfileImage: String? = null,
    val createdAt: Long = 0,
)
