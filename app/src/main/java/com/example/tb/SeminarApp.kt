package com.example.tb

import androidx.compose.runtime.Composable

import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.kacamata.ui.screens.RuanganScreen

import com.example.tb.ui.screens.*

@Composable
fun SeminarApp() {
    val navController = rememberNavController()
    val PeminjamanState = State()

    NavHost(navController, startDestination = "landing") {
        composable("login") {LoginScreen(navController, PeminjamanState) }
        composable("ruangan") {RuanganScreen(navController, PeminjamanState) }
         composable("deskripsi") {DeskripsiScreen(navController, PeminjamanState)}
        composable("jadwalTersedia") {DeskripsiScreen(navController, PeminjamanState)}
        composable("pengajuan") {PengajuanScreen(navController, PeminjamanState)}


    }
}






