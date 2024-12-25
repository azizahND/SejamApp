package com.example.tb

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



interface ApiService {


    @POST("auth/login")
    suspend fun login(@Body loginRequest: Map<String, String>): Response<User>

    @GET("ruangan/daftarRuangan")
    suspend fun getRuanganList(): Response<RuanganResponse>

    @POST("/review")
    suspend fun createReview(@Body review: ReviewRequest): Response<Unit>

}