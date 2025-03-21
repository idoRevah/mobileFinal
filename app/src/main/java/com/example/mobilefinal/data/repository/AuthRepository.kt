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

    // 🔹 Login user with email & password
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

    // 🔹 Register user & store in Firestore
    suspend fun register(email: String, password: String): Result<User> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = auth.currentUser ?: return Result.failure(Exception("User is null"))

            val user = User(id = firebaseUser.uid, email = firebaseUser.email ?: "")
            firestore.document(user.id).set(user).await() // Save user to Firestore
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 🔹 Log out user
    fun logout() {
        auth.signOut()
    }

    // 🔹 Check if user is logged in
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
