package com.example.sejam.ui.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejam.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

data class BookingFormState(
    val startTime: LocalDate? = null,
    val endTime: LocalDate? = null,
    val purpose: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val bookingCreated: Boolean = false
) {
    fun isFormValid(): Boolean {
        return startTime != null &&
                endTime != null &&
                purpose.isNotBlank() &&
                startTime.isBefore(endTime)
    }
}


class BookingViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BookingFormState())
    val state: StateFlow<BookingFormState> = _state

    fun showStartDatePicker(context: Context) {
        val currentDate = LocalDate.now()

        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = LocalDate.of(year, month + 1, day)
                _state.update { it.copy(startTime = selectedDate) }
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }

    fun showEndDatePicker(context: Context) {
        val currentDate = LocalDate.now()

        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = LocalDate.of(year, month + 1, day)
                _state.update { it.copy(endTime = selectedDate) }
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }

    fun updatePurpose(purpose: String) {
        _state.update { it.copy(purpose = purpose) }
    }

    fun createBooking(roomId: String) {
        val currentState = _state.value

        if (!currentState.isFormValid()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.createBooking(
                    roomId = roomId,
                    startTime = currentState.startTime!!,
                    endTime = currentState.endTime!!,
                    purpose = currentState.purpose
                )
                _state.update {
                    it.copy(
                        isLoading = false,
                        bookingCreated = true
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to create booking"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun resetState() {
        _state.value = BookingFormState()
    }
}