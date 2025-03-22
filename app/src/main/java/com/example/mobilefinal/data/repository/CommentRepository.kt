package com.example.mobilefinal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mobilefinal.data.MobileFinalDatabase
import com.example.mobilefinal.data.dao.CommentDao
import com.example.mobilefinal.data.model.Comment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class CommentRepository {
    private val commentDao: CommentDao = MobileFinalDatabase.getDatabase().commentDao()
    private val firestore = Firebase.firestore.collection("comments")

    suspend fun addComment(comment: Comment) {
        comment.authorProfileImage = UserRepository().getUserByUid(comment.authorUserId)?.profile_picture

        commentDao.insert(comment)
        try {
            firestore.document(comment.id)
                .set(comment)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCommentsForWorkoutWithSync(workoutId: String): LiveData<List<Comment>> = liveData {
        val localComments = commentDao.getCommentsForWorkout(workoutId)
        emitSource(localComments)

        syncCommentsFromFirestore(workoutId)
    }

    private suspend fun syncCommentsFromFirestore(workoutId: String) {
        try {
            val snapshot = firestore
                .whereEqualTo("workoutId", workoutId)
                .get()
                .await()

            val remoteComments = snapshot.toObjects(Comment::class.java)

            if (remoteComments.isNotEmpty()) {
                remoteComments.forEach { commentDao.insert(it) }
                val remoteIds = remoteComments.map { it.id }
                commentDao.deleteCommentsNotIn(remoteIds)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
