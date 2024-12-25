package com.example.sejam.ui.reviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejam.data.UserRepository
import com.example.sejam.data.response.ReviewsItem
import com.example.sejam.data.response.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _reviews = MutableStateFlow<Room?>(null)
    val reviews: StateFlow<Room?> = _reviews.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun getReviews(roomId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getReviews(roomId)
                _reviews.value = response.room
                Log.d("ReviewViewModel", "Reviews: ${response.room}")
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createReview(roomId: String, comment: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.createReview(roomId, comment)
                // Refresh reviews after creating new one
                getReviews(roomId)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}