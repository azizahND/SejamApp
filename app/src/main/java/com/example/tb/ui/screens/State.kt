package com.example.tb.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class State {
    var errorMessage by mutableStateOf("")
    var ruang by mutableStateOf("")
    var deskripsi by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var pilihan by mutableStateOf("")
    var login by mutableStateOf("")
    var nama by mutableStateOf("")
    var nim by mutableStateOf("")
    var organisasi by mutableStateOf("")
    var kegiatan by mutableStateOf("")
    var surat by mutableStateOf("")
    var review by mutableStateOf("")


}