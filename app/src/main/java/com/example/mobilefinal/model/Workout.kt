package com.example.mobilefinal.model

class Workout (
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val exercises: List<Exercise> = emptyList()
)