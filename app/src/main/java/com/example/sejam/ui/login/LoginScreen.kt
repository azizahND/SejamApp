package com.example.sejam.ui.login

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sejam.R
import com.example.sejam.ViewModelFactory
import com.example.sejam.data.pref.UserModel


@Composable
fun LoginScreen(context: Context, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginViewModel: LoginViewModel = viewModel(factory = ViewModelFactory.getInstance(context))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background image at the bottom
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Welcome Text
            Text(
                text = "Selamat Datang",
                fontSize = 28.sp,
                color = Color(0xFF1B5E20), // Dark Green
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Sejam",
                fontSize = 20.sp,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email Field
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp)), // Light green background
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Email Unand", color = Color(0xFF1B5E20)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Email",
                        tint = Color(0xFF1B5E20)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password Field
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp)), // Light green background
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Password", color = Color(0xFF1B5E20)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password",
                        tint = Color(0xFF1B5E20)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color(0xFF1B5E20)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = { loginViewModel.login(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B5E20)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 16.sp)
            }

            val loginState by loginViewModel.loginResult.observeAsState()

            LaunchedEffect(loginState) {
                loginState?.let { result ->
                    result.onSuccess { response ->
                        loginViewModel.saveSession(
                            UserModel(
                                token = response.token,
                                isLogin = true
                            )
                        )
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}
