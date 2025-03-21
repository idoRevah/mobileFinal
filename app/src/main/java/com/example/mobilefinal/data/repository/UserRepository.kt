package com.example.mobilefinal.data.repository

import com.example.mobilefinal.data.MobileFinalDatabase
import com.example.mobilefinal.data.dto.UserDto
import com.example.mobilefinal.data.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UserRepository {
    private val usersDao = MobileFinalDatabase.getDatabase().userDao()
    private val firestoreHandle = Firebase.firestore.collection("user")

    suspend fun upsertUser(user: User) = withContext(Dispatchers.IO) {
        firestoreHandle.document(user.id).set(user).await()
        usersDao.upsertAll(user)
    }

    suspend fun cacheUsersIfNotExisting(uids: List<String>) = withContext(Dispatchers.IO) {
        val existingUserIds = usersDao.getExistingUserIds(uids)
        val nonExistingUserIds = uids.filter { !existingUserIds.contains(it) }

        this@UserRepository.getUsersFromRemoteSource(nonExistingUserIds)
    }

    suspend fun getUserByUid(uid: String): User? = withContext(Dispatchers.IO) {
        val cachedResult = usersDao.getUserByUid(uid)
        if (cachedResult != null) return@withContext cachedResult

        return@withContext this@UserRepository.getUserFromRemoteSource(uid)
    }

    private suspend fun getUserFromRemoteSource(uid: String): User? =
        withContext(Dispatchers.IO) {
            val user =
                firestoreHandle.document(uid).get().await().toObject(UserDto::class.java)?.toUser()

            if (user != null) {
                usersDao.upsertAll(user)
            }

            return@withContext user
        }

    suspend fun getUsersFromRemoteSource(uids: List<String>): List<User> =
        withContext(Dispatchers.IO) {
            val usersQuery =
                if (uids.isNotEmpty()) firestoreHandle.whereIn("id", uids) else firestoreHandle
            val users = usersQuery.get().await().toObjects(UserDto::class.java).map {it.toUser()}

            if (users.isNotEmpty()) {
                usersDao.upsertAll(*users.toTypedArray())
            }

            return@withContext users
        }
}