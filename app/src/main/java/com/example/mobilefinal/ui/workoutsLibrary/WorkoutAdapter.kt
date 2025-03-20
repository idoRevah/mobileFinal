package com.example.mobilefinal.ui.workoutsLibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobilefinal.R
import com.example.mobilefinal.data.model.Workout

class WorkoutAdapter(
    private var workouts: List<Workout>,
    private val onClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewWorkout)
        val titleView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionView: TextView = itemView.findViewById(R.id.text_view_name)
    }

    // Invokes on Initialize, creating enough views to fill the screen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        // Translate the XML to View with layout inflater
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    // Binds data from the list to the viewHolder, according what suppose to be visible
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.titleView.text = workout.title
        holder.descriptionView.text = workout.description
        // TODO: load the image
        Glide.with(holder.itemView.context).load(workout.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onClick(workout)
        }

    }

    override fun getItemCount(): Int = workouts.size

    fun updateData(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }

    // TODO: add delete/ insert data functions if needed (with notifyItemInserted)
}