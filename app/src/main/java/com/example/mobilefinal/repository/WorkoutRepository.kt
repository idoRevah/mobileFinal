package com.example.mobilefinal.repository

import android.util.Log
import com.example.mobilefinal.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val db = FirebaseFirestore.getInstance()

    private val mockWorkouts = listOf(
        Workout(
            id = "1",
            title = "Full Body Workout",
            description = "A workout for the entire body, focusing on strength and endurance.",
            imageUrl = "https://hips.hearstapps.com/hmg-prod/images/young-muscular-build-athlete-exercising-strength-in-royalty-free-image-1706546541.jpg?crop=0.668xw:1.00xh;0.107xw,0&resize=640:*"
        ),
        Workout(
            id = "2",
            title = "Upper Body Workout",
            description = "A workout targeting arms, shoulders, and chest muscles.",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRw8Rj7MbJKM2nNwtQs_23TsW1Y-wbmvoMwdg&s"
        ),
        Workout(
            id = "3",
            title = "Lower Body Workout",
            description = "A workout focused on legs and glutes.",
            imageUrl = "https://i.ytimg.com/vi/gey73xiS8F4/maxresdefault.jpg"
        ),
        Workout(
            id = "4",
            title = "Cardio Blast",
            description = "High-intensity cardio exercises to boost heart rate.",
            imageUrl = "https://www.purefitness.com/media/tzsiojs5/one-hour-gym-header-image.jpg?quality=80"
        ),
        Workout(
            id = "5",
            title = "Yoga Flow",
            description = "Relaxing yoga poses to improve flexibility and mindfulness.",
            imageUrl = "https://example.com/yoga_flow.jpg"
        )
    )

    suspend fun getWorkouts(): List<Workout> {
//            return mockWorkouts

            return try {
            val querySnapshot = db.collection("workouts").get().await()
            val workouts = mutableListOf<Workout>()

            for (document in querySnapshot.documents) {
                val workout = document.toObject(Workout::class.java)
                workout?.let {
                    workouts.add(it)
                }
            }

            return workouts
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Error getting workouts", e)
            emptyList()
        }
    }

    suspend fun createWorkout(workout: Workout) {
        try {
            // Auto-generate document ID if not provided
            val workoutId = workout.id ?: db.collection("workouts").document().id
            val workoutWithId = workout.copy(id = workoutId)

            // Add the workout to Firestore with the correct ID
            db.collection("workouts")
                .document(workoutId)
                .set(workoutWithId)
                .await()
            Log.d("WorkoutRepository", "Workout created successfully with ID: $workoutId")
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Error creating workout", e)
            throw e // Re-throw the exception to handle it higher up
        }
    }

    suspend fun likeWorkout(workoutId: String) {
        try {
            val workoutRef = db.collection("workouts").document(workoutId)
            db.runTransaction { transaction ->
                val snapshot = transaction.get(workoutRef)
                val currentLikes = snapshot.getLong("likes") ?: 0
                transaction.update(workoutRef, "likes", currentLikes + 1)
            }.await()

            Log.d("WorkoutRepository", "Like added to workout with ID: $workoutId")
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Error adding like to workout", e)
            throw e
        }
    }

}