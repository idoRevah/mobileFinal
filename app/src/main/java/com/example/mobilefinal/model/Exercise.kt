package com.example.mobilefinal.model

data class Exercise(
    val id: String = "", // should be reconsidered - looks unused
//    val imageUrl: String = "", // should be reconsidered - maybe from generic list
//    val duration: Int = 0, // should be reconsidered - maybe 5m for each one for build workout
    val name: String = "", // must have
    val description: String = "", // must have
    var muscle: String = "", // must have
    val difficulty: String = "", // should be reconsidered - comes from the api and looks unused
    val equipment: String = "", // should be reconsidered - comes from the api and looks unused
    val type: String = "", // should be reconsidered - comes from the api and looks unused
    )