package com.example.mobilefinal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mobilefinal.data.MobileFinalDatabase
import com.example.mobilefinal.data.model.Workout
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val workoutDao = MobileFinalDatabase.getDatabase().workoutDao()
    private val firestoreHandle = FirebaseFirestore.getInstance().collection("workouts")

    private val db = FirebaseFirestore.getInstance()

    fun getAllWorkouts(): LiveData<List<Workout>> {
        syncWorkoutsFromFirebase()
        return workoutDao.getAllWorkouts()
    }

    fun getWorkoutById(workoutId: String): LiveData<Workout>? {
        return workoutDao.getWorkoutById(workoutId)
    }

    private fun syncWorkoutsFromFirebase() {
        firestoreHandle.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener

            Log.d("ola", snapshot.documents.toString())
            val workouts =
                snapshot.documents.mapNotNull { it.toObject(Workout::class.java)?.copy(id = it.id) }

            if (workouts.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    workoutDao.insertWorkouts(workouts)
                }
            }
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

    suspend fun likeWorkout(workoutId: String) {
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