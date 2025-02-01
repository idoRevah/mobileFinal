package com.example.mobilefinal.model

data class Exercise(
    val id: String = "", // should be reconsidered - looks unused
    val gifUrl: String = "", // should be reconsidered - maybe from generic list
    val name: String = "", // must have
    val description: String = "", // must have
    var muscle: String = "", // must have
    )