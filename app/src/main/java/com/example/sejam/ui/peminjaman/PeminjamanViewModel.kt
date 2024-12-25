package com.example.sejam.ui.peminjaman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejam.data.UserRepository
import com.example.sejam.data.response.Bookings
import com.example.sejam.data.response.MyBookingsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PeminjamanViewModel(private val repository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow<List<MyBookingsItem>>(emptyList())
    val state: StateFlow<List<MyBookingsItem>> = _state.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing.asStateFlow()

    private val _detail = MutableStateFlow<Bookings?>(null)
    val detail: StateFlow<Bookings?> = _detail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchBookings()
    }

    private fun fetchBookings() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val bookings = repository.getBookings().bookings
                _state.value = bookings
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to fetch bookings"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadBookingDetail(bookingId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val booking = repository.getBookingDetail(bookingId)
                _detail.value = booking.bookings
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load booking detail"
                _detail.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleEdit() {
        _isEditing.value = !_isEditing.value
    }

    fun updateBooking(bookingId: String, startTime: String, endTime: String, purpose: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Call repository update method
                repository.updateBooking(
                    bookingId = bookingId.toInt(),
                    startTime = startTime,
                    endTime = endTime,
                    purpose = purpose
                )

                _isEditing.value = false
                loadBookingDetail(bookingId)

                // Refresh the bookings list
                fetchBookings()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update booking"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}