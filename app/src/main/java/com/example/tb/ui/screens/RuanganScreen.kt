package com.example.kacamata.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tb.R // Pastikan impor ini benar
import com.example.tb.ui.screens.State




@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RuanganScreen(navController: NavHostController, orderState: State) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, top = 70.dp, end = 50.dp, bottom = 2.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        listOf("Seminar Sistem Informasi", "Seminar Teknik Komputer", "Seminar Informatika").forEach { ruangan ->
            Button(
                onClick = {
                    orderState.ruang = ruangan
                    navController.navigate("deskripsi")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .width(120.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF527853)              )
            ) {
                Text(
                    text = ruangan,
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }




    }
    Column ( modifier = Modifier
        .fillMaxSize()
        .padding(start = 0.dp, top = 400.dp, end = 0.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        Spacer(modifier = Modifier.height(20.dp))
        val image: Painter = painterResource(id = R.drawable.gambar1)
        Box(
            modifier = Modifier
                .fillMaxSize() // Pastikan Box memenuhi seluruh layar atau area induk
        ) {
            Image(
                painter = image,
                contentDescription = "Gambar 1",
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Menempatkan gambar di kanan bawah
                    .height(400.dp)
                    .width(400.dp)


                    .padding(0.dp)

                    .graphicsLayer(
                        scaleX = -1f,
                        translationX = 0f, // Menggeser gambar ke kanan untuk menampilkan bagian tertentu

                    ),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRuanganScreen() {
    // Dummy NavController and State for Preview
    val navController = rememberNavController()
    val orderState = State()

    RuanganScreen(navController = navController, orderState = orderState)
}
