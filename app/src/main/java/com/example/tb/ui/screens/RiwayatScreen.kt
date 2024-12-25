package com.example.tb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class PeminjamanItem(
    val title: String,
    val date: String,
    val time: String,
    val status: String
)

@Composable
fun RiwayatPeminjamanScreen() {
    val riwayatList = listOf(
        PeminjamanItem("Seminar SI", "Selasa, 12 Oktober 2024", "13:30 WIB - 16:00 WIB", "Disetujui"),
        PeminjamanItem("Seminar SI", "Rabu, 13 Oktober 2024", "13:30 WIB - 16:00 WIB", "Disetujui"),
        PeminjamanItem("Seminar SI", "Kamis, 14 Oktober 2024", "13:30 WIB - 16:00 WIB", "Disetujui"),
        PeminjamanItem("Seminar SI", "Jumat, 15 Oktober 2024", "13:30 WIB - 16:00 WIB", "Disetujui"),
        PeminjamanItem("Seminar SI", "Sabtu, 16 Oktober 2024", "13:30 WIB - 16:00 WIB", "Ditolak")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Riwayat Peminjaman",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            riwayatList.forEach { item ->
                RiwayatPeminjamanItem(item)
            }
        }
    }
}

@Composable
fun RiwayatPeminjamanItem(item: PeminjamanItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = item.date, fontSize = 14.sp)
            Text(text = item.time, fontSize = 14.sp)
        }

        // Status Box
        val statusColor = when (item.status) {
            "Disetujui" -> Color(0xFF4CAF50) // Green
            "Ditolak" -> Color(0xFFFF5252) // Red
            else -> Color.Gray
        }

        Box(
            modifier = Modifier
                .background(statusColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.status, fontSize = 14.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Action Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { /* Edit Action */ }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.Yellow)
            }
            IconButton(onClick = { /* Delete Action */ }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
            IconButton(onClick = { /* Info Action */ }) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info", tint = Color.Blue)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRiwayatPeminjamanScreen() {
    RiwayatPeminjamanScreen()
}
