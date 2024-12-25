package com.example.sejam.di

import android.content.Context
import com.example.sejam.data.UserRepository
import com.example.sejam.data.pref.UserPreference
import com.example.sejam.data.pref.dataStore
import com.example.sejam.data.remote.ApiConfig


    object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()

        return UserRepository.getInstance(apiService, pref)

    }
}