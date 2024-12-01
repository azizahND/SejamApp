package com.example.tb.ui.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.tb.R
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JadwalScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val today = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Calendar Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "October 2024",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            // Calendar Grid
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                CalendarGrid(
                    onDateSelected = { selectedDate = it },
                    selectedDate = selectedDate
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Legend Section
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Keterangan:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(color = Color.Green, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Telah diajukan", fontSize = 14.sp)
                }
            }
        }

        // Submit Button
        Button(
            onClick = { /* Handle Ajukan Peminjaman */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Ajukan Peminjaman", color = Color.White)
        }

        // Illustration Section
        Spacer(modifier = Modifier.height(16.dp))
        BasicText(text = "") // Replace this with an actual image or vector drawable
    }
    Column ( modifier = Modifier
        .fillMaxSize()
        .padding(start = 0.dp, top = 500.dp, end = 0.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        Spacer(modifier = Modifier.height(20.dp))
        val image: Painter = painterResource(id = R.drawable.jadwal)
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


                    .padding(0.dp),


                contentScale = ContentScale.Crop
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate?
) {
    val daysInMonth = (1..31).toList()
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Column {
        // Weekday Headers
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (day in daysOfWeek) {
                Text(text = day, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        // Calendar Days
        for (week in daysInMonth.chunked(7)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                for (day in week) {
                    val isSelected = selectedDate?.dayOfMonth == day
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = if (isSelected) Color.Green else Color.Transparent,
                                shape = CircleShape
                            )
                            .padding(4.dp)
                            .clickable { onDateSelected(LocalDate.of(2024, 10, day)) }
                    ) {
                        Text(
                            text = day.toString(),
                            fontSize = 14.sp,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewJadwalScreen() {
    JadwalScreen()
}
