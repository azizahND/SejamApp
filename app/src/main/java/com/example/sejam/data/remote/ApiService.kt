package com.example.sejam.data.remote

import com.example.sejam.data.request.BookingRequest
import com.example.sejam.data.request.LoginRequest
import com.example.sejam.data.request.ReviewRequest
import com.example.sejam.data.response.BookingsResponse
import com.example.sejam.data.response.DetailBookingsResponse
import com.example.sejam.data.response.DetailRoomsResponse
import com.example.sejam.data.response.LoginResponse
import com.example.sejam.data.response.ReviewsResponse
import com.example.sejam.data.response.RoomResponse
import com.example.sejam.data.response.RoomsResponse
import com.example.sejam.data.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): UserResponse

    @GET("/rooms")
    suspend fun getRooms(): RoomsResponse

    @GET("/rooms/{roomId}")
    suspend fun getRoom(@Path("roomId") roomId: String): DetailRoomsResponse


    @POST("/bookings/{roomId}")
    suspend fun createBooking(
        @Header ("Authorization") token: String,
        @Path("roomId") roomId: String,
        @Body bookingRequest: BookingRequest
    ): Response<Void>

    @GET("/reviews/{roomId}")
    suspend fun getReviews(@Path("roomId") roomId: String): ReviewsResponse

    @POST("/reviews/{roomId}")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Path("roomId") roomId: String,
        @Body reviewRequest: ReviewRequest
    ): Response<Void>

    @GET("/bookings")
    suspend fun getBookings(@Header("Authorization") token: String): BookingsResponse

    @GET("/bookings/{bookingId}")
    suspend fun getBookingDetail(
        @Path("bookingId") bookingId: String
    ): DetailBookingsResponse


    @PUT("/bookings/{bookingId}")
    suspend fun updateBooking(
        @Path("bookingId") bookingId: String,
        @Body bookingRequest: BookingRequest
    ): Response<Void>

    @GET("/panduan/panduan.pdf")
    suspend fun downloadPanduan(
    ): Response<ResponseBody>
}

