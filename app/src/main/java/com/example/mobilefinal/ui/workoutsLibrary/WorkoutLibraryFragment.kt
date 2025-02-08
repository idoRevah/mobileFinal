package com.example.mobilefinal.ui.workoutsLibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilefinal.R
import com.example.mobilefinal.databinding.FragmentWorkoutLibraryBinding
import com.example.mobilefinal.model.Workout

class WorkoutLibraryFragment : Fragment() {

    private var _binding: FragmentWorkoutLibraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WorkoutLibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WorkoutLibraryViewModel::class.java)

        val adapter = WorkoutAdapter(emptyList()) { workout ->
            val bundle = Bundle().apply {
                putString("workoutId", workout.id)
            }

            findNavController().navigate(R.id.action_workoutLibraryFragment_to_exerciseListFragment, bundle)

        }

        binding.recyclerViewWorkouts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWorkouts.adapter = adapter

        viewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.updateData(workouts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
