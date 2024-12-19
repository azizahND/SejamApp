package com.example.tb.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tb.ApiClient
import com.example.tb.ApiService
import com.example.tb.LoginState
import com.example.tb.Ruangan
import com.example.tb.RuanganResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    // State untuk login
    private val _loginState = mutableStateOf(LoginState())
    val loginState get() = _loginState // Getter untuk akses di luar ViewModel



    // API Service instance
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/") // Ganti dengan IP server backend Anda
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Fungsi login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true) // Set loading state
            try {
                val response = apiService.login(mapOf("email" to email, "password" to password))
                if (response.isSuccessful) {
                    val user = response.body()
                    _loginState.value = _loginState.value.copy(
                        user = user,
                        isLoading = false,
                        errorMessage = null
                    )
                } else {
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        errorMessage = "Login failed: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(
                    isLoading = false,
                    errorMessage = "An error occurred: ${e.message}"
                )
            }
        }
    }






        // Fungsi untuk mendapatkan daftar ruangan
        private val _ruanganState = MutableLiveData<List<String>>(emptyList())
    val ruanganState: LiveData<List<String>> = _ruanganState

    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _errorState = MutableLiveData("")
    val errorState: LiveData<String> = _errorState

    fun fetchRuanganList() {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                val response = apiService.getRuanganList()
                if (response.isSuccessful) {
                    val body = response.body()
                    _ruanganState.value = body?.ruangan ?: emptyList()
                    _errorState.value = ""
                } else {
                    _errorState.value = "Gagal memuat data"
                }
            } catch (e: Exception) {
                _errorState.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _loadingState.value = false
            }
        }
    }
        }










