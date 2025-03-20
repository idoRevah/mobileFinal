package com.example.mobilefinal.ui.workoutsLibrary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.model.Workout
import com.example.mobilefinal.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutLibraryViewModel: ViewModel() {

    private val repository = WorkoutRepository()

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    init {
        this.fetchWorkouts()
    }

    private fun fetchWorkouts() {
        viewModelScope.launch {
            try {
                val fetchedWorkouts = repository.getWorkouts()
                _workouts.value = fetchedWorkouts
            } catch (e: Exception) {
                Log.e("WorkoutLibraryViewModel", "Error fetching workouts", e)
            }
        }
    }
}