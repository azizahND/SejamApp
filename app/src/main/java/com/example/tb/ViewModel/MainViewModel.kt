package com.example.tb.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    var message: String? = null

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getData()

                if (response.isSuccessful) {
                    // Mengakses properti message dari response.body()
                    message = response.body()?.message // Menggunakan safe call untuk mengakses message
                } else {
                    message = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                message = "Error: ${e.message}"
            }
        }
    }
    }


data class ApiResponse(val message: String) // Tidak ada perubahan, karena sudah benar


