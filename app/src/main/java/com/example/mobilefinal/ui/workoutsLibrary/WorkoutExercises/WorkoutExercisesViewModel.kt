package com.example.mobilefinal.ui.workoutsLibrary.WorkoutExercises

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.model.Exercise
import com.example.mobilefinal.model.Workout
import com.example.mobilefinal.repository.ExerciseRepository
import com.example.mobilefinal.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutExercisesViewModel(private val state: SavedStateHandle) : ViewModel() {
    private val workoutId: String = state["workoutId"] ?: ""
    private val exercisesRepository: ExerciseRepository = ExerciseRepository()
    private val workoutRepository: WorkoutRepository = WorkoutRepository()
    private val _workout = MutableLiveData<Workout>()
    private val _workoutExercises = MutableLiveData<List<Exercise>>()
    val workoutExercises: LiveData<List<Exercise>> get() = _workoutExercises

    init {
        loadWorkoutExercises(workoutId)
    }

    fun loadWorkoutExercises(workoutId: String) {
        viewModelScope.launch { // Starts a coroutine (background thread)
            try {
                val workout = WorkoutRepository().getWorkoutById(workoutId) ?: return@launch
                _workout.postValue(workout) // ✅ This runs ONLY AFTER getWorkoutById() finishes

                val exercises = workout.exerciseIds.mapNotNull { id ->
                    ExerciseRepository().getExerciseById(id).getOrNull() // ✅ This runs ONLY AFTER workout.exerciseIds is available
                }

                _workoutExercises.postValue(exercises) // ✅ This runs ONLY AFTER all exercises are fetched

            } catch (e: Exception) {
                Log.e("WorkoutViewModel", "Error loading workout and exercises", e)
            }
        }
    }

}