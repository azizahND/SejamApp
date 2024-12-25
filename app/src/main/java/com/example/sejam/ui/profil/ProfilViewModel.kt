package com.example.sejam.ui.profil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejam.data.UserRepository
import com.example.sejam.data.response.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfilViewModel (private val repository: UserRepository) : ViewModel() {


    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    fun fetchUser() {
        viewModelScope.launch {
            try {
                val response = repository.getUser().user
                _user.value = response
                Log.d("RegisterEventViewModel", "User data fetched: $response")
            } catch (e: Exception) {
                _user.value = null
                // Log error or handle appropriately
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


    // Data state untuk profil
    data class ProfileState(val username: String, val email: String)
}
