package com.example.mobilefinal.ui.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.repository.UserRepository
import com.example.mobilefinal.model.User
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUser(uid: String): LiveData<User?> = userRepository.getUser(uid)

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        userRepository.updateUser(user)
    }

    fun updateUserProfile(displayName: String?, profileImageUrl: String?) = viewModelScope.launch {
        userRepository.updateUserProfile(displayName, profileImageUrl)
    }

    fun getCurrentUser(): User? {
        return userRepository.getCurrentUser()
    }
}