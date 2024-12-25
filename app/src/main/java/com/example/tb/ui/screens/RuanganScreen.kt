package com.example.kacamata.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tb.R // Pastikan impor ini benar
import com.example.tb.ViewModel.MainViewModel
import com.example.tb.ui.screens.State




@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RuanganScreen(navController: NavHostController, viewModel: MainViewModel) {
    // Mengamati LiveData dari ViewModel
    val ruanganList by viewModel.ruanganState.observeAsState(emptyList())
    val loadingState by viewModel.loadingState.observeAsState(false)
    val errorState by viewModel.errorState.observeAsState("")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Daftar Ruangan") },
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Loading indicator
                if (loadingState) {
                    CircularProgressIndicator()
                }

                // Error message
                if (errorState.isNotEmpty()) {
                    Text(
                        text = errorState,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // List of buttons for each room
                ruanganList.forEach { ruangan ->
                    Button(
                        onClick = {
                            navController.navigate("deskripsi/$ruangan")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = ruangan,
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Bagian untuk menampilkan gambar di bawah
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, top = 400.dp, end = 0.dp, bottom = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            val image: Painter = painterResource(id = R.drawable.gambar1)
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = image,
                    contentDescription = "Gambar 1",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(400.dp)
                        .width(400.dp)
                        .graphicsLayer(
                            scaleX = -1f,
                            translationX = 0f
                        ),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun RuanganScreenWrapper(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()

    // Panggil fetchRuanganList() untuk memuat data saat composable pertama kali dimuat
    LaunchedEffect(Unit) {
        viewModel.fetchRuanganList()
    }

    // Panggil RuanganScreen dengan ViewModel yang sudah ada
    RuanganScreen(navController = navController, viewModel = viewModel)
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewRuanganScreen() {
//    // Dummy NavController untuk Preview
//    val navController = rememberNavController()
//
//    // Menggunakan ViewModel dari Hilt (atau DI lainnya)
//    val viewModel = object : MainViewModel() {
//        override fun fetchRuanganList() {
//            _ruanganState.value = listOf(
//                Ruangan(nama_ruangan = "Ruang A"),
//                Ruangan(nama_ruangan = "Ruang B"),
//                Ruangan(nama_ruangan = "Ruang C")
//            )
//        }
//    }
//
//    // Panggil RuanganScreen dengan ViewModel
//    RuanganScreen(navController = navController, viewModel = viewModel)
//}




