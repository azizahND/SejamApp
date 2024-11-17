package com.example.tb.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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

fun LoginScreen (navController: NavHostController, State: State){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 50.dp, end = 30.dp),

        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    )
    {
        Text(
            text = "Selamat Datang",
            style = TextStyle(
           
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color(0xFF326A34) // Warna dalam format hex
            ),
            modifier = Modifier.padding(5.dp)
        )



        Text(
            text = "Sejam",
            style = TextStyle(

                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF326A34)
            ),
            modifier = Modifier.padding(5.dp)
        )


    }

Column (
    modifier = Modifier
        .fillMaxSize()
        .padding(start = 30.dp, top = 150.dp, end = 30.dp),

    horizontalAlignment = Alignment.End,
    verticalArrangement = Arrangement.Top
)
{
    // Input untuk Email
    OutlinedTextField(
        value = State.email,
        onValueChange = { State.email = it },
        label = { Text("Email") },
        placeholder = { Text("Masukkan Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                    unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

    ),
    shape = RoundedCornerShape(20.dp)

    )


    OutlinedTextField(
        value = State.password,
        onValueChange = { State.password  = it},
        label = { Text("Password") },
        placeholder = { Text("Masukkan Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
        unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

        ),
    shape = RoundedCornerShape(20.dp)
    )
    val login = "Login"
    val validEmail = "jijah"
    val validPassword = "jijahkiw"

    Button(

        onClick = {
            if (State.email == validEmail && State.password == validPassword) {
                navController.navigate("ruangan")
            } else {
                // Tampilkan pesan kesalahan (misalnya, menggunakan Snackbar atau Toast)
                State.errorMessage = "Email atau password salah. Silakan coba lagi."
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp)
            .width(120.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF326A34)
        )
    ) {
        Text(
            text = login,
            color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
    Column ( modifier = Modifier
        .fillMaxSize()
        .padding(start = 0.dp, top = 400.dp, end = 0.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        Spacer(modifier = Modifier.height(20.dp))
        val image: Painter = painterResource(id = R.drawable.login)
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


    @Preview(showBackground = true)
    @Composable
    fun PreviewLoginScreen() {

        val navController = rememberNavController()
        val State = State()

        LoginScreen(navController = navController, State = State)
    }