package com.example.mobilefinal.ui.exerciseDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.mobilefinal.databinding.FragmentExerciseDetailsBinding
import com.example.mobilefinal.data.model.Exercise

class ExerciseDetailsFragment : Fragment() {
    private var _binding: FragmentExerciseDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExerciseDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exercise.observe(viewLifecycleOwner) { exercise ->
            updateUI(exercise)
        }
    }

    private fun updateUI(exercise: Exercise) {
        exercise?.let {
            binding.textViewName.text = it.name
            binding.textViewTargetMuscles.text = it.muscle
            binding.textViewSecondaryMuscles.text = it.secondaryMuscles
            binding.textViewEquipment.text = it.equipment
            binding.textViewDescription.text = it.description

            Log.d("GlideDebug", "Image URL: ${it.gifUrl}")
            Glide.with(this)
                .load(it.gifUrl)
                .into(binding.imageViewExerciseGif)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}