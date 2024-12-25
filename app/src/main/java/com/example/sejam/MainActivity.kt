package com.example.sejam

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sejam.data.pref.UserPreference
import com.example.sejam.data.pref.dataStore
import com.example.sejam.ui.booking.BookingFormScreen
import com.example.sejam.ui.booking.BookingListScreen
import com.example.sejam.ui.home.HomeScreen
import com.example.sejam.ui.login.LoginScreen
import com.example.sejam.ui.peminjaman.PeminjamanDetailScreen
import com.example.sejam.ui.peminjaman.PeminjamanScreen
import com.example.sejam.ui.profil.ProfilScreen
import com.example.sejam.ui.reviews.ReviewScreen
import com.example.sejam.ui.room.RoomDetailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            SejamApp()
        }
    }

}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SejamApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreference = UserPreference.getInstance(context.dataStore)
    val userSession = userPreference.getSession().collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userSession.value) {
        val isLoggedIn = userSession.value?.isLogin == true
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
        Log.d("SejamApp", "isLoggedIn: $isLoggedIn")
    }

    AnimatedNavHost(navController = navController, startDestination = if (userSession.value?.isLogin == true) "home" else "login") {
        composable(
            "login",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { LoginScreen(context, navController) }
        composable(
            "home",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { HomeScreen(navController = navController) }
        composable(
            "profile",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { ProfilScreen(navController = navController) }
        composable(
            "booking",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { PeminjamanScreen(navController = navController) }
        composable(
            "bookingDetail/{bookingId}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId")
            if (bookingId != null) {
                PeminjamanDetailScreen(bookingId = bookingId, navController = navController)
            }
        }
        composable(
            "room_detail/{roomId}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId")
            if (roomId != null) {
                RoomDetailScreen(roomId = roomId, navController = navController)
            }
        }
        composable(
            "booking_form/{roomId}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ){ backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId")
            if (roomId != null) {
                BookingFormScreen(roomId = roomId, navController = navController)
            }
        }
        composable(
            "booking_list/{roomId}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ){ backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId")
            if (roomId != null) {
                BookingListScreen(roomId = roomId, navController = navController)
            }
        }
        composable(
            "review/{roomId}",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ){ backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId")
            if (roomId != null) {
                ReviewScreen(roomId = roomId, navController = navController)
            }
        }



    }
}