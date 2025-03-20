package com.example.mobilefinal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val exerciseIds: List<String> = emptyList(),
    val likes: Int = 0
)
