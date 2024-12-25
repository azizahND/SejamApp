package com.example.sejam

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sejam.data.UserRepository
import com.example.sejam.di.Injection
import com.example.sejam.ui.booking.BookingViewModel
import com.example.sejam.ui.home.HomeViewModel
import com.example.sejam.ui.login.LoginViewModel
import com.example.sejam.ui.peminjaman.PeminjamanViewModel
import com.example.sejam.ui.profil.ProfilViewModel
import com.example.sejam.ui.reviews.ReviewViewModel
import com.example.sejam.ui.room.RoomDetailViewModel


class ViewModelFactory(private val repository: UserRepository,private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository,context) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RoomDetailViewModel::class.java) -> {
                RoomDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BookingViewModel::class.java) -> {
                BookingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ReviewViewModel::class.java) -> {
                ReviewViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfilViewModel::class.java) -> {
                ProfilViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PeminjamanViewModel::class.java) -> {
                PeminjamanViewModel(repository) as T
            }









            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    context.applicationContext // Gunakan applicationContext untuk menghindari memory leak
                ).also { INSTANCE = it }
            }
        }
    }
}