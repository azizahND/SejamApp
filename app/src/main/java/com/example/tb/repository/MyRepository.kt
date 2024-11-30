package com.example.tb.repository
import com.example.tb.model.MyDataModel

class MyRepository {
    private val api = RetrofitInstance.api

    suspend fun fetchData(): List<MyDataModel>? {
        val response = api.getData()
        return if (response.isSuccessful) response.body() else null
    }
}