package com.example.mobilefinal.data.dto

import com.example.mobilefinal.data.model.User

data class UserDto(
    val email: String = "",
    val display_name: String = "",
    val profile_picture : String? = null,
    val id: String? = null
) {
    fun toUser(): User {
        return User(
            id=id ?: "",
            email = email,
            display_name = display_name,
            profile_picture = profile_picture
        )
    }
}