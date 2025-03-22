package com.example.mobilefinal.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.model.User
import com.example.mobilefinal.data.repository.AuthRepository
import com.example.mobilefinal.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private val authRepository: AuthRepository = AuthRepository()
    private val userRepository: UserRepository = UserRepository()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            try {
                val firebaseUser = authRepository.getCurrentUser()
                if (firebaseUser != null) {
                    val cachedUser = userRepository.getUserByUid(firebaseUser.id)
                    _user.postValue(cachedUser)
                } else {
                    _error.postValue("No user logged in")
                }
            } catch (e: Exception) {
                _error.postValue("Error fetching user: ${e.message}")
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _user.value = null
    }

    fun saveChanges(display_name: String, updatedImageBase64: String?) {
        viewModelScope.launch {
            val currentUser = _user.value ?: return@launch
            val updatedUser = currentUser.copy(
                display_name = display_name,
                profile_picture = updatedImageBase64
            )
            try {
                userRepository.upsertUser(updatedUser)
                _user.postValue(updatedUser)
            } catch (e: Exception) {
                _error.postValue("Failed to update profile: ${e.message}")
            }
        }
    }

}
