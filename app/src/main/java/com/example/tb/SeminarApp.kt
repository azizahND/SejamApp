package com.example.tb

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kacamata.ui.screens.RuanganScreen
import com.example.tb.ViewModel.MainViewModel
import com.example.tb.ui.screens.*

@Composable
fun SeminarApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel() // Ambil instance ViewModel

    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, mainViewModel) }
        composable("pilihan") { RuanganScreen(navController, mainViewModel) }


    }
}
