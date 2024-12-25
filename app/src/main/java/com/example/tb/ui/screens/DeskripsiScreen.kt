package com.example.tb.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.tb.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun DeskripsiScreen (navController: NavHostController, State: State) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, top = 0.dp, end = 0.dp),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    )
    {
        val image: Painter = painterResource(id = R.drawable.ruangan)
        Box(
            modifier = Modifier
                .fillMaxSize() // Pastikan Box memenuhi seluruh layar atau area induk
        ) {
            Image(
                painter = image,
                contentDescription = "Gambar 1",
                modifier = Modifier
                    .align(Alignment.TopStart) // Menempatkan gambar di kanan bawah
                    .height(400.dp)
                    .width(400.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))


                    .padding(0.dp)

                    .graphicsLayer(
                        scaleX = -1f,
                        translationX = 0f, // Menggeser gambar ke kanan untuk menampilkan bagian tertentu

                    ),
                contentScale = ContentScale.Crop

            )





        }

    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 0.dp, end = 30.dp),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    )
    {
        Spacer(modifier = Modifier.height(600.dp))

        listOf("Jadwal Tersedia", "Ajukan Peminjaman").forEach { pilihan ->
            Button(
                onClick = {
                    State.pilihan = pilihan
                    val route = when (pilihan) {
                        "Jadwal Tersedia" -> ""
                        "Ajukan Peminjaman" -> "Pengajuan"
                        else -> ""
                    }
                    navController.navigate(route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .width(100.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF326A34)
                )
            ) {
                Text(
                    text = pilihan,
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp, // Mengatur teks menjadi bold
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
    }
@Preview(showBackground = true)
@Composable
fun PreviewDeskripsiScreen() {

    val navController = rememberNavController()
    val State = State()

    DeskripsiScreen(navController = navController, State = State)
}