package com.example.mobilefinal.model

import com.google.firebase.firestore.DocumentId

data class Workout (
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val exerciseIds: List<String> = emptyList(),
    val likes: Int = 0
)
