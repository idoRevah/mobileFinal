package com.example.mobilefinal.data.model

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobilefinal.data.dto.UserDto
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = "",
    val email: String = "",
    val display_name: String = "",
//    val profile_picture: String?
) {
    fun toUserDto(): UserDto {
        return UserDto(
            id = id,
            email = email,
            display_name = display_name,
//            profile_picture = profile_picture
        )
    }

    companion object {

        suspend fun fromFirebaseAuth(context: Context): User? {
            val user = FirebaseAuth.getInstance().currentUser ?: return null
            return User(
                id = user.uid,
                email = user.email ?: "",
                display_name = user.displayName ?: ""
            )
        }
//
//        suspend fun downloadImageAndConvertToBase64Suspend(
//            imageUrl: String,
//            context: Context
//        ): String {
//            return suspendCancellableCoroutine { continuation ->
//                downloadImageAndConvertToBase64(imageUrl, context) { base64String ->
//                    continuation.resume(base64String ?: "") { throwable ->
//                        continuation.resumeWithException(throwable)
//                    }
//                }
//            }
//        }

    }
}