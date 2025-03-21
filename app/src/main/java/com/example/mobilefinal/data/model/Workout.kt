package com.example.mobilefinal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mobilefinal.data.converters.StringListConverter
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "workouts")
@TypeConverters(StringListConverter::class)
data class Workout(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val exerciseIds: List<String> = emptyList(),
    val likes: Int = 0
)
