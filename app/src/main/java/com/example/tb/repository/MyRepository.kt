package com.example.tb.repository
import com.example.tb.ViewModel.ApiResponse
import com.example.tb.model.MyDataModel

class MyRepository {
    private val api = RetrofitInstance.api

    suspend fun fetchData(): ApiResponse? {
        val response = api.getData()
        return if (response.isSuccessful) response.body() else null
    }
}

