package com.example.mobilefinal.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilefinal.R
import com.example.mobilefinal.model.Exercise
import com.example.mobilefinal.model.Workout

class ExerciseAdapter(private val exercises: List<Exercise>, private val onClick: (Exercise) -> Unit) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewExercise)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)

        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.textViewTitle.text = exercise.name
        holder.textViewDescription.text = exercise.description
        holder.imageView.setImageResource(R.drawable.ex) // Default Image
        holder.itemView.setOnClickListener {onClick(exercise)
        }
    }

    override fun getItemCount() = exercises.size
}
