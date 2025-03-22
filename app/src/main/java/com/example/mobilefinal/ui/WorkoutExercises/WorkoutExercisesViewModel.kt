package com.example.mobilefinal.ui.WorkoutExercises

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.model.Exercise
import com.example.mobilefinal.data.model.Workout
import com.example.mobilefinal.data.repository.ExerciseRepository
import com.example.mobilefinal.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutExercisesViewModel(private val state: SavedStateHandle) : ViewModel() {
    private val workoutId: String = state["workoutId"] ?: ""
    private val exercisesRepository: ExerciseRepository = ExerciseRepository()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val workoutRepository: WorkoutRepository = WorkoutRepository()
    private val currWorkout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = currWorkout
    private val _workoutExercises = MutableLiveData<List<Exercise>?>()
    val workoutExercises: LiveData<List<Exercise>?> get() = _workoutExercises

    init {
        loadWorkoutExercises(workoutId)
    }

    fun loadWorkoutExercises(workoutId: String) {
        _isLoading.postValue(true)

        workoutRepository.getWorkoutById(workoutId)?.observeForever { workout ->
            currWorkout.postValue(workout)

            if (workout == null) {
                Log.e("WorkoutViewModel", "Workout not found")
                _isLoading.postValue(false)
                return@observeForever
            }

            viewModelScope.launch {
                val exercises = workout.exerciseIds.mapNotNull { id ->
                    exercisesRepository.getExerciseById(id).getOrNull()
                }
                _workoutExercises.postValue(exercises)
                _isLoading.postValue(false)
            }
        }
    }

}