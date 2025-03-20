package com.example.mobilefinal.ui.WorkoutExercises

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilefinal.R
import com.example.mobilefinal.adapters.WorkoutExercisesListAdapter
import com.example.mobilefinal.databinding.FragmentExerciseListBinding
import com.example.mobilefinal.data.model.Exercise

class ExerciseListFragment: Fragment() {

    private lateinit var binding: FragmentExerciseListBinding
    private lateinit var exerciseAdapter: WorkoutExercisesListAdapter
    private val viewModel: WorkoutExercisesViewModel by viewModels()
    private val exercises = mutableListOf<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
          setupRecyclerView()

        // bind between recyclerview to the viewmodel's workouts
        viewModel.workoutExercises.observe(viewLifecycleOwner) { exercises ->
            if (exercises != null) {
                exerciseAdapter.updateData(exercises)
            }
        }
    }

    private fun getWorkoutIdFromArgs(): Int {
        val args = ExerciseListFragmentArgs.fromBundle(requireArguments())

        return args.workoutId
    }

    private fun setupRecyclerView() {
        exerciseAdapter = WorkoutExercisesListAdapter(exercises) { exerciseId -> navigateToExerciseDetails(exerciseId)}
        binding.recyclerViewExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }
    }

    private fun navigateToExerciseDetails(exerciseId: String) {
        val bundle = Bundle().apply {
            putString("exerciseId", exerciseId)
        }

        findNavController().navigate(R.id.action_exerciseListFragment_to_exerciseDetailsFragment, bundle)
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