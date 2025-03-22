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
}