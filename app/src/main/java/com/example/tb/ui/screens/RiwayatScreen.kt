package com.example.tb.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RiwayatPeminjamanScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item
    ) {

        // Menampilkan event
        EventItem(
            title = "Seminar SI",
            date = "Senin, 16 Oktober 2024",
            time = "13.30 WIB - 16.00 WIB",
            status = "Disetujui"
        )
        EventItem(
            title = "Seminar SI",
            date = "Selasa, 17 Oktober 2024",
            time = "13.30 WIB - 16.00 WIB",
            status = "Disetujui"
        )
        EventItem(
            title = "Seminar SI",
            date = "Rabu, 18 Oktober 2024",
            time = "13.30 WIB - 16.00 WIB",
            status = "Disetujui"
        )
        EventItem(
            title = "Seminar SI",
            date = "Kamis, 19 Oktober 2024",
            time = "13.30 WIB - 16.00 WIB",
            status = "Disetujui"
        )
        EventItem(
            title = "Seminar SI",
            date = "Jumat, 20 Oktober 2024",
            time = "13.30 WIB - 16.00 WIB",
            status = "Ditolak"
        )
    }
}

@Composable
fun EventItem(
    title: String,
    date: String,
    time: String,
    status: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Menambahkan padding di sekitar Box
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // Vertikal rata tengah
            horizontalArrangement = Arrangement.SpaceBetween // Mengatur jarak antara elemen di dalam Row
        ) {
            // Kolom kiri untuk Title, Date, dan Time
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp) // Jarak antar teks dalam kolom
            ) {
                Text(title, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(date)
                Text(time)
            }
            // Teks status di sebelah kanan
            Text(
                status,
                color = if (status == "Ditolak") Color.Red else Color.Green, // Warna berdasarkan status
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewRiwayatPeminjamanScreen() {
    RiwayatPeminjamanScreen() // Tampilkan tampilan utama di preview
}