package com.example.tb.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PengajuanScreen (navController: NavHostController, State: State) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 80.dp, end = 30.dp),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    )
    {
        Text(
            text = "Nama",
            modifier = Modifier.padding(bottom = 2.dp),
            style = TextStyle(

                fontSize = 20.sp,
        )
        )

        OutlinedTextField(
            value = State.nama,
            onValueChange = { State.nama = it },


            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                ),
            shape = RoundedCornerShape(20.dp)

        )
        Text(
            text = "NIM",
            modifier = Modifier.padding(bottom = 2.dp),
            style = TextStyle(

                fontSize = 20.sp,
            )
        )
        OutlinedTextField(
            value = State.nim,
            onValueChange = { State.nim = it },


            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                ),
            shape = RoundedCornerShape(20.dp)

        )
        Text(
            text = "Organisasi",
            modifier = Modifier.padding(bottom = 2.dp),
            style = TextStyle(

                fontSize = 20.sp,
            )
        )
        OutlinedTextField(
            value = State.organisasi,
            onValueChange = { State.organisasi = it },


            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                ),
            shape = RoundedCornerShape(20.dp)

        )
        Text(
            text = "Kegiatan",
            modifier = Modifier.padding(bottom = 2.dp),
            style = TextStyle(

                fontSize = 20.sp,
            )
        )
        OutlinedTextField(
            value = State.kegiatan,
            onValueChange = { State.kegiatan = it },


            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                ),
            shape = RoundedCornerShape(20.dp)

        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            // Kolom Kiri
            Column(
                modifier = Modifier
                    .weight(1f) // Menggunakan bobot agar lebar sama
                    .padding(end = 20.dp) // Jarak ke kolom kanan
            ) {
                Text(
                    text = "Jam :",
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = TextStyle(

                        fontSize = 20.sp,
                    )
                )
                OutlinedTextField(
                    value = State.kegiatan,
                    onValueChange = { State.kegiatan = it },


                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                        unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                        ),
                    shape = RoundedCornerShape(20.dp)

                )

                // Tambahkan elemen lain di kolom kiri di sini
            }

            // Kolom Kanan
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )

            {
                Text(
                    text = "Sampai :",
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = TextStyle(

                        fontSize = 20.sp,
                    )
                )
                OutlinedTextField(
                    value = State.kegiatan,
                    onValueChange = { State.kegiatan = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                        unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                        ),
                    shape = RoundedCornerShape(20.dp)

                )



            }

        }
        Text(
            text = "Unggah File Surat",
            modifier = Modifier.padding(top = 10.dp),
            style = TextStyle(

                fontSize = 20.sp,
            )
        )
        OutlinedTextField(
            value = "",  // Placeholder untuk nama file yang diunggah
            onValueChange = {},
            readOnly = true,
            label = { Text("Pilih file surat") },
            placeholder = { Text("Tidak ada file yang dipilih") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                ),
            shape = RoundedCornerShape(20.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("deskripsi")
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF326A34)
                )
            ) {
                Text("Ajukan",
                        style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                    )
            }
        }

}
}

@Preview(showBackground = true)
@Composable
fun PreviewPengajuanScreen() {

    val navController = rememberNavController()
    val State = State()


    PengajuanScreen(navController = navController, State = State)

}