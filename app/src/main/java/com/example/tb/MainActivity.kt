package com.example.tb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tb.ViewModel.MainViewModel
import com.example.tb.ui.theme.TBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            var message by remember { mutableStateOf("Fetching...") }

            // Fetch data saat komponen diluncurkan
            LaunchedEffect(Unit) {
                viewModel.fetchData()
                message = viewModel.message ?: "No data"
            }

            // Tema aplikasi
            TBTheme {
                // Konten aplikasi
                SeminarApp()
            }
        }
    }
}



