package com.example.mobilefinal.data.model

import java.time.LocalDateTime

//import com.google.firebase.firestore.DocumentId

data class Comment (
//    @DocumentId
    val id: String = "",
    val workoutId: Int = 0,
    val userId: String = "",
    val imageBase64: String = "",
    val text: List<String> = emptyList(),
    val createDateTime: LocalDateTime
)
