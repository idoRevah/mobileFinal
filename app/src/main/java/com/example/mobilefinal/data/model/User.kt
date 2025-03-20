package com.example.mobilefinal.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val email: String = "",
    val displayName: String? = null,
    val photoUrl: String? = null
)
