package com.example.mobilefinal.ui.workoutThread

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.model.Comment
import com.example.mobilefinal.data.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel () : ViewModel() {
    private val repository: CommentRepository = CommentRepository()

    fun getComments(workoutId: String): LiveData<List<Comment>> {
        return repository.getCommentsForWorkoutWithSync(workoutId)
    }

    fun addComment(comment: Comment) {
        viewModelScope.launch {
            repository.addComment(comment)
        }
    }

    fun deleteComment(commentId: String) {
        viewModelScope.launch {
            repository.deleteCommentById(commentId)
        }
    }

    fun updateComment(comment: Comment) {
        viewModelScope.launch {
            repository.updateComment(comment)
        }
    }}
