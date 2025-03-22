package com.example.mobilefinal.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobilefinal.data.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: Comment)

    @Query("SELECT * FROM comments WHERE workoutId = :workoutId ORDER BY createdAt DESC")
    fun getCommentsForWorkout(workoutId: String): LiveData<List<Comment>>

    @Query("DELETE FROM comments WHERE id NOT IN (:commentIds)")
    suspend fun deleteCommentsNotIn(commentIds: List<String>)
}
