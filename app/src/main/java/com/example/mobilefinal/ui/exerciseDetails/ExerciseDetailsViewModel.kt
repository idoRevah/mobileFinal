package com.example.mobilefinal.ui.exerciseDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.model.Exercise
import com.example.mobilefinal.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseDetailsViewModel(private val state: SavedStateHandle) : ViewModel() {
    private val exercisesRepository: ExerciseRepository = ExerciseRepository()
    private val exerciseId: String = state["exerciseId"] ?: ""
    private val _exercise = MutableLiveData<Exercise>()
    val exercise: LiveData<Exercise> get() = _exercise

    init {
        loadExercise(exerciseId)
    }

    fun loadExercise(exerciseId: String) {
        viewModelScope.launch {
            try {
                exercisesRepository.getExerciseById(exerciseId).getOrNull()?.let { exercise ->

                    _exercise.postValue(exercise)
                }
            } catch (e: Exception) {
                Log.e("ExerciseDetailsViewModel", "Error loading exercise", e)
            }

        }
    }
}