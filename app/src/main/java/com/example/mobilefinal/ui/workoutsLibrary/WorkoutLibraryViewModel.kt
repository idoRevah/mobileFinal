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
    val workouts: LiveData<List<Workout>> = repository.getAllWorkouts()

}