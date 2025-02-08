package com.example.mobilefinal.ui.workoutsLibrary.Exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilefinal.R
import com.example.mobilefinal.adapters.ExerciseAdapter
import com.example.mobilefinal.databinding.FragmentExerciseListBinding
import com.example.mobilefinal.model.Exercise

class ExerciseListFragment: Fragment() {

    private lateinit var binding: FragmentExerciseListBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exercises = mutableListOf<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Back Navigation

//        binding.toolbar.setNavigationOnClickListener {
//            findNavController().navigateUp()
//        }

        // Get workout ID from arguments
        val workoutId = arguments?.getString("workoutId") ?: return

        // Load exercises for this workout
        exercises.addAll(getExercisesForWorkout(workoutId))

        // Setup RecyclerView


        exerciseAdapter = ExerciseAdapter(exercises){ exercise ->
            val bundle = Bundle().apply {
                putString("exerciseId", exercise.id)
            }

            findNavController().navigate(R.id.exerciseDetailsFragment, bundle)

        }


        binding.recyclerViewExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }
    }

    private fun getExercisesForWorkout(workoutId: String): List<Exercise> {
        return listOf(
            Exercise(name="Push Ups", description = "Chest & Arms"),
            Exercise(name = "Squats", description = "Legs & Core"),
            Exercise(name = "Plank", description = "Core Stability"),
            Exercise(name = "Jumping Jacks", description = "Full Body Warm-up")
        )
    }
}