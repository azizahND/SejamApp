package com.example.tb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PanduanPeminjamanScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Text(
            text = "Panduan Peminjaman",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Smaller Empty Document Content Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp) // Mengubah ukuran kotak menjadi lebih kecil
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Kotak ini kosong
        }

        // Download Button
        Button(
            onClick = { /* Handle Download Action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Download", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPanduanPeminjamanScreen() {
    PanduanPeminjamanScreen()
}
