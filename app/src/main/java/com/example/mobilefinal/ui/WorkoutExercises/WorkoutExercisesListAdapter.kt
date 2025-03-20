package com.example.mobilefinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilefinal.R
import com.example.mobilefinal.databinding.ItemExerciseBinding
import com.example.mobilefinal.data.model.Exercise

class WorkoutExercisesListAdapter(
    private var exercises: List<Exercise>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<WorkoutExercisesListAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.textViewTitle.text = exercise.name
            binding.textViewMuscle.text = exercise.muscle

            binding.imageViewExercise.setImageResource(R.drawable.ex)

            binding.root.setOnClickListener {
                onItemClick(exercise.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount() = exercises.size

    fun updateData(newExercises: List<Exercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
}
