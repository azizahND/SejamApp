package com.example.sejam.ui.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sejam.ViewModelFactory
import com.example.sejam.ui.componen.BottomNavBar
import com.example.sejam.ui.theme.Primary
import com.example.sejam.ui.theme.Secondary

@Composable
fun ProfilScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfilViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val userData by viewModel.user.collectAsState()



    viewModel.fetchUser()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Profile Header Section with curved bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(bottom = 50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logout Button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(
                        onClick = {
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo("profil") { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }

                // Profile Picture
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Secondary)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }

        // Profile Information Cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-30).dp)
        ) {
            ProfileInfoCard(
                title = "Personal Information",
                content = listOf(
                    "Name" to (userData?.username ?: ""),
                    "NIM" to (userData?.nim ?: ""),
                    "Email" to (userData?.email ?: "")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileInfoCard(
                title = "Security",
                content = listOf(
                    "Password" to "••••••••"
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        BottomNavBar(navController = navController)
    }
}

@Composable
private fun ProfileInfoCard(
    title: String,
    content: List<Pair<String, String>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = Primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            content.forEach { (label, value) ->
                Column {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                    if (content.last().first != label) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}