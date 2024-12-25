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
import androidx.compose.runtime.remember
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
import com.example.tb.ViewModel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController, viewModel: MainViewModel) {

    val loginState = viewModel.loginState.value
    val state = remember { State() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 50.dp, end = 30.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Selamat Datang",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF326A34))
        )
        Text(
            text = "Silakan Login",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF326A34))
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { state.email = it },
            label = { Text("Email") },
            placeholder = { Text("Masukkan Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f)
            ),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { state.password = it },
            label = { Text("Password") },
            placeholder = { Text("Masukkan Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f)
            ),
            shape = RoundedCornerShape(20.dp)
        )

        if (loginState.isLoading) {
            Text("Loading...", color = Color.Gray, modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                viewModel.login(state.email, state.password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Login", color = Color.White)
        }

        loginState.errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        loginState.user?.let {
            navController.navigate("pilihan") // Navigate on successful login
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    val dummyViewModel = MainViewModel() // Gunakan ViewModel dummy untuk pratinjau

    LoginScreen(navController = navController, viewModel = dummyViewModel)
}