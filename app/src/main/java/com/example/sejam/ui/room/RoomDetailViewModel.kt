package com.example.sejam.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejam.data.UserRepository
import com.example.sejam.data.response.DataItemRooms
import com.example.sejam.data.response.DetailRoomsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RoomDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val room: DetailRoomsData? = null
)

class RoomDetailViewModel(private val repository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow(RoomDetailState())
    val state: StateFlow<RoomDetailState> = _state

    fun loadRoomDetail(roomId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val room = repository.getRoom(roomId).data
                _state.update { it.copy(isLoading = false, room = room) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load room details"
                    )
                }
            }
        }
    }

    fun retryLoading() {
        _state.value.room?.id?.toString()?.let { roomId ->
            loadRoomDetail(roomId)
        }
    }
}