package com.example.mobilefinal.ui.workoutsLibrary.WorkoutExercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilefinal.adapters.ExerciseAdapter
import com.example.mobilefinal.databinding.FragmentExerciseListBinding
import com.example.mobilefinal.model.Exercise

class ExerciseListFragment: Fragment() {

    private lateinit var binding: FragmentExerciseListBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
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
        
        // Back Navigation

//        binding.toolbar.setNavigationOnClickListener {
//            findNavController().navigateUp()
//        }

        setupRecyclerView()

        // bind between recyclerview to the viewmodel's workouts
        viewModel.workoutExercises.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.updateData(exercises)
        }
    }

    private fun getWorkoutIdFromArgs(): String {
        val args = ExerciseListFragmentArgs.fromBundle(requireArguments())

        return args.workoutId
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(exercises)
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