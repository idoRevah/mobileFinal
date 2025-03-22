package com.example.mobilefinal.ui.workoutThread

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilefinal.R
import com.example.mobilefinal.data.model.Comment
import com.example.mobilefinal.data.repository.AuthRepository
import com.example.mobilefinal.data.repository.CommentRepository
import com.example.mobilefinal.databinding.ItemThreadBinding
import com.example.mobilefinal.utils.ImageUtils
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CommentAdapter(
    private val onDeleteClick: (Comment) -> Unit,
    private val onEditClick: (Comment) -> Unit
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val commentsList = mutableListOf<Comment>()

    fun submitList(list: List<Comment>) {
        commentsList.clear()
        commentsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class CommentViewHolder(private val binding: ItemThreadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            binding.textViewUsername.text = comment.authorNickname
            binding.textViewOpinion.text = comment.content
            binding.textViewTimestamp.text = getTimeAgo(comment.createdAt)

            loadProfileImage(comment)
            loadCommentImage(comment)

            val currentUserId = AuthRepository().getCurrentUser()?.id
            binding.actionButtonsContainer.visibility =
                if (comment.authorUserId == currentUserId) View.VISIBLE else View.GONE

            binding.btnDeleteComment.setOnClickListener {
                onDeleteClick(comment)
            }

            binding.btnEditComment.setOnClickListener {
                onEditClick(comment)
            }
        }

        private fun loadProfileImage(comment: Comment) {
            if (comment.authorProfileImage != null && comment.authorProfileImage != "") {
                val profileBitmap = ImageUtils.base64ToBitmap(comment.authorProfileImage!!)
                binding.imageViewUserProfile.setImageBitmap(profileBitmap)
            } else {
                binding.imageViewUserProfile.setImageResource(R.drawable.ic_user_placeholder)
            }
        }

        private fun loadCommentImage(comment: Comment) {
            if (comment.image != null && comment.image != "") {
                val commentBitmap = ImageUtils.base64ToBitmap(comment.image!!)
                binding.imageViewThreadImage.visibility = View.VISIBLE
                binding.imageViewThreadImage.setImageBitmap(commentBitmap)
            } else {
                binding.imageViewThreadImage.visibility = View.GONE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemThreadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    override fun getItemCount(): Int = commentsList.size

    private fun getTimeAgo(timeMillis: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timeMillis

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "just now"
            diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)}m ago"
            diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)}h ago"
            else -> {
                val date = Date(timeMillis)
                SimpleDateFormat("dd MMM", Locale.getDefault()).format(date)
            }
        }
    }
}
