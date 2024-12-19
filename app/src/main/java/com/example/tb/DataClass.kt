package com.example.tb

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)

// Data class untuk user
data class User(
    val id_user: Int,
    val email: String,
    val nama: String,
    val role: String
)

data class Ruangan(
    val nama_ruangan: List<String>
)

data class RuanganResponse(
    val message: String,
    val ruangan: List<String>
)

data class ReviewRequest(
    val id_ruangan: String,
    val id_user: String,
    val review: String
)

