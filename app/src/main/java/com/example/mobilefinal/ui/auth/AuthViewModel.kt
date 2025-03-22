package com.example.mobilefinal.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.data.model.User
import com.example.mobilefinal.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _authError = MutableLiveData<String?>()
    val authError: LiveData<String?> get() = _authError

    // 🔹 Check if user is already logged in
    fun checkUserStatus() {
        if (authRepository.isUserLoggedIn()) {
            _user.value = User(authRepository.getCurrentUser()?.id ?: "", authRepository.getCurrentUser()?.email ?: "")
        }
    }

    // 🔹 Register user
    fun register(email: String, password: String, profile_picture: String) {
        viewModelScope.launch {
            val result = authRepository.register(email, password, profile_picture)
            result.onSuccess { _user.postValue(it) }
                .onFailure { _authError.postValue(it.message) }
        }
    }

    // 🔹 Log in user
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.onSuccess { _user.postValue(it) }
                .onFailure { _authError.postValue(it.message) }
        }
    }

    // 🔹 Log out user
    fun logout() {
        authRepository.logout()
        _user.value = null
    }
}
