package com.example.mobilefinal.data.repository

import com.example.mobilefinal.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore.collection("users")

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = auth.currentUser ?: return Result.failure(Exception("User is null"))

            val user = User(id = firebaseUser.uid, email = firebaseUser.email ?: "")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String, base64Image: String? = null): Result<User> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = auth.currentUser ?: return Result.failure(Exception("User is null"))

            val user = User(id = firebaseUser.uid, email = firebaseUser.email ?: "", profile_picture = base64Image)
            UserRepository().upsertUser(user)

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            User(id = it.uid, email = it.email ?: "")
        }
    }

}
