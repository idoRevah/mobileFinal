package com.example.mobilefinal.data.repository

import android.util.Log
import com.example.mobilefinal.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getWorkouts(): List<Workout> {
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

    suspend fun getWorkoutById(workoutId: Int): Workout? {
// TODO: fix the workoutId.tostring...
         try {
            val documentSnapshot = db.collection("workouts")
                .document(workoutId.toString())
                .get()
                .await()

             return documentSnapshot.toObject(Workout::class.java)
        } catch (e: Exception) {
            Log.e("WorkoutRepository", "Error fetching workout by ID", e)
             return null
        }
    }

    suspend fun createWorkout(workout: Workout) {
//        try {
//            // Auto-generate document ID if not provided
//            val workoutId = workout.id ?: db.collection("workouts").document().id
//            val workoutWithId = workout.copy(id = workoutId)
//
//            // Add the workout to Firestore with the correct ID
//            db.collection("workouts")
//                .document(workoutId)
//                .set(workoutWithId)
//                .await()
//            Log.d("WorkoutRepository", "Workout created successfully with ID: $workoutId")
//        } catch (e: Exception) {
//            Log.e("WorkoutRepository", "Error creating workout", e)
//            throw e // Re-throw the exception to handle it higher up
//        }
    }

    suspend fun likeWorkout(workoutId: Int) {
//        try {
//            val workoutRef = db.collection("workouts").document(workoutId)
//            db.runTransaction { transaction ->
//                val snapshot = transaction.get(workoutRef)
//                val currentLikes = snapshot.getLong("likes") ?: 0
//                transaction.update(workoutRef, "likes", currentLikes + 1)
//            }.await()
//
//            Log.d("WorkoutRepository", "Like added to workout with ID: $workoutId")
//        } catch (e: Exception) {
//            Log.e("WorkoutRepository", "Error adding like to workout", e)
//            throw e
//        }
    }

}