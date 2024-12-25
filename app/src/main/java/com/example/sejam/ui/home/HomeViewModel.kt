package com.example.sejam.ui.home

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sejam.data.UserRepository
import com.example.sejam.data.response.DataItemRooms
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel(private val repository: UserRepository,
                    private val context: Context) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadBookings()
    }

    private val _downloadStatus = MutableStateFlow<DownloadStatus>(DownloadStatus.Idle)
    val downloadStatus: StateFlow<DownloadStatus> = _downloadStatus.asStateFlow()


    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun loadBookings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val rooms = repository.getRooms().data
                _state.update {
                    it.copy(
                        isLoading = false,
                        rooms = rooms,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    private fun monitorDownload(downloadId: Long) {
        viewModelScope.launch {
            var downloading = true
            while (downloading) {
                val query = DownloadManager.Query().setFilterById(downloadId)
                val cursor = (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
                    .query(query)

                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                    when (status) {
                        DownloadManager.STATUS_SUCCESSFUL -> downloading = false
                        DownloadManager.STATUS_FAILED -> {
                            downloading = false
                            _downloadStatus.value = DownloadStatus.Error("Download failed")
                        }
                    }
                }
                cursor.close()
                delay(1000) // Check every second
            }
        }
    }

    fun download() {
        viewModelScope.launch {
            try {
                _downloadStatus.value = DownloadStatus.Loading

                val response = repository.downloadPanduan()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val contentType = response.headers()["Content-Type"] ?: "application/pdf"  // Ensure it's set to PDF
                    val fileName = "panduan peminjaman.pdf"  // Use .pdf extension

                    responseBody?.let {
                        // Create download request using the actual PDF URL
                        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                        val request = DownloadManager.Request(Uri.parse("https://sejam.techfuture.my.id/panduan/panduan.pdf"))  // Updated to point to PDF
                            .setMimeType(contentType)
                            .setTitle("Downloading response letter")
                            .setDescription("Downloading response letter file...")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true)

                        try {
                            val downloadId = downloadManager.enqueue(request)
                            _downloadStatus.value = DownloadStatus.Success

                            // Optional: Monitor download progress
                            monitorDownload(downloadId)
                        } catch (e: Exception) {
                            _downloadStatus.value = DownloadStatus.Error("Failed to start download: ${e.message}")
                        }
                    }
                } else {
                    _downloadStatus.value = DownloadStatus.Error("Download failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _downloadStatus.value = DownloadStatus.Error("Error downloading proposal: ${e.message}")
            }
        }
    }

    fun retryLoading() {
        loadBookings()
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val rooms: List<DataItemRooms> = emptyList(),
    val error: String? = null
)

sealed class DownloadStatus {
    object Idle : DownloadStatus()
    object Loading : DownloadStatus()
    object Success : DownloadStatus()
    data class Error(val message: String) : DownloadStatus()
}

