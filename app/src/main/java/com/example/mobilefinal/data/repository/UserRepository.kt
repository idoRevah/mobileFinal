package com.example.mobilefinal.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.mobilefinal.data.dao.UserDao
import com.example.mobilefinal.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun getUser(uid: String): LiveData<User?> = userDao.getUser(uid)

    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.updateUser(user)
    }

    suspend fun updateUserProfile(displayName: String?, profileImageUrl: String?) {
        val currentUser = firebaseAuth.currentUser ?: return
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .setPhotoUri(profileImageUrl?.let { Uri.parse(it) })
            .build()

        currentUser.updateProfile(profileUpdates)
        val updatedUser = User(currentUser.uid, currentUser.displayName, currentUser.email, currentUser.photoUrl?.toString())
        insertUser(updatedUser)
    }

    fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            return User(firebaseUser.uid, firebaseUser.displayName, firebaseUser.email, firebaseUser.photoUrl?.toString())
        }
        return null
    }
}