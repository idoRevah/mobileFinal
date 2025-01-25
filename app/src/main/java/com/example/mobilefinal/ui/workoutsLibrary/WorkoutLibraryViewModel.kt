package com.example.mobilefinal.ui.workoutsLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilefinal.model.Workout
import com.example.mobilefinal.repository.WorkoutRepository

class WorkoutLibraryViewModel: ViewModel() {

    private val repository = WorkoutRepository()

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    init {
        fetchWorkouts()
    }

    private fun fetchWorkouts() {
        _workouts.value = repository.getWorkouts()
    }
}