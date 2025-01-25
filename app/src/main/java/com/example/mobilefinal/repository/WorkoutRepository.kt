package com.example.mobilefinal.repository

import com.example.mobilefinal.model.Workout

class WorkoutRepository {
    private val mockWorkouts = listOf(
        Workout(
            id = "1",
            title = "Full Body Workout",
            description = "A workout for the entire body, focusing on strength and endurance.",
            imageUrl = "https://example.com/full_body_workout.jpg"
        ),
        Workout(
            id = "2",
            title = "Upper Body Workout",
            description = "A workout targeting arms, shoulders, and chest muscles.",
            imageUrl = "https://example.com/upper_body_workout.jpg"
        ),
        Workout(
            id = "3",
            title = "Lower Body Workout",
            description = "A workout focused on legs and glutes.",
            imageUrl = "https://example.com/lower_body_workout.jpg"
        ),
        Workout(
            id = "4",
            title = "Cardio Blast",
            description = "High-intensity cardio exercises to boost heart rate.",
            imageUrl = "https://example.com/cardio_blast.jpg"
        ),
        Workout(
            id = "5",
            title = "Yoga Flow",
            description = "Relaxing yoga poses to improve flexibility and mindfulness.",
            imageUrl = "https://example.com/yoga_flow.jpg"
        )
    )

    // TODO: implement as workouts repository from backend
    fun getWorkouts(onSuccess: (List<Workout>) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            onSuccess(mockWorkouts)
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}