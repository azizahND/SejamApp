package com.example.sejam.ui.componen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sejam.R
import com.example.sejam.ui.theme.BackGround
import com.example.sejam.ui.theme.Primary

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(BackGround),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Room Tab
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Home, // Menggunakan ikon "Home" untuk Room
                contentDescription = "home",
                tint = Primary,
            )
        }

        // Booking Tab
        IconButton(
            onClick = { navController.navigate("booking") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Bookmark, // Menggunakan ikon "Bookmark" untuk Booking
                contentDescription = "Bookings",
                tint = Primary
            )
        }

        // Profile Tab
        IconButton(
            onClick = { navController.navigate("profile") },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Person, // Menggunakan ikon "Person" untuk Profile
                contentDescription = "Profile",
                tint = Primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    BottomNavBar(navController = navController)
}
