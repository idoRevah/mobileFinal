package com.example.mobilefinal.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinal.repository.AuthRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class AuthViewModel() : ViewModel() {

    private val repository: AuthRepository = AuthRepository()
    private val _authState = MutableLiveData<Boolean>() // True if logged in
    val authState: LiveData<Boolean> get() = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            _authState.value = result.isSuccess
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.register(email, password)
            _authState.value = result.isSuccess
        }
    }

    fun logout() {
        repository.logout()
        _authState.value = false
    }

    fun checkUserLoggedIn() {
        _authState.value = repository.isUserLoggedIn()
    }
}
