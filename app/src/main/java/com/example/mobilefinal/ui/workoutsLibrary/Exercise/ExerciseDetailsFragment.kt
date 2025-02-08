package com.example.mobilefinal.ui.workoutsLibrary.Exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mobilefinal.R
import com.example.mobilefinal.databinding.FragmentExerciseDetailsBinding
import com.example.mobilefinal.model.Exercise
import com.example.mobilefinal.repository.ExerciseRepository
import com.example.mobilefinal.repository.WorkoutRepository

class ExerciseDetailsFragment : Fragment() {

    private lateinit var binding: FragmentExerciseDetailsBinding
    private val repository = ExerciseRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Get Exercise Data
        val exerciseId = arguments?.getString("exerciseId") ?: return

        // TODO: get data from repository
        val exercise:Exercise = repository.getExerciseById(exerciseId)

        // Set Data to UI
        binding.textViewDescription.text = exercise.description
        binding.textViewTargetMuscles.text = exercise.targetMuscles
        binding.textViewSecondaryMuscles.text = exercise.secondaryMuscles
        binding.textViewEquipment.text = exercise.equipment

        // Load GIF
        Glide.with(this)
            .asGif()
            .load(exercise.gifUrl)
            .error(R.drawable.ex)
            .into(binding.imageViewExerciseGif)
    }
}
