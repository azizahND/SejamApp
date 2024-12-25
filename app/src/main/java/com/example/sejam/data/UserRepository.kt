package com.example.sejam.data

import android.util.Log
import com.example.sejam.data.pref.UserModel
import com.example.sejam.data.pref.UserPreference

import com.example.sejam.data.remote.ApiService
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {


    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(LoginRequest(email,password))
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getUser(): UserResponse {
        val token = getToken()
        try {

            return apiService.getUser("Bearer $token")
        } catch (e: HttpException) {
            if (e.code() == 401) {
                logout()
                throw e
            } else {
                throw e
            }
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    private fun getToken(): String {
        val user = runBlocking { userPreference.getSession().first() }
        Log.d("UserRepository", "Token: ${user.token}")
        return runBlocking { userPreference.getSession().first().token }
    }

    suspend fun getRooms(): RoomsResponse {
        return apiService.getRooms()
    }

    suspend fun getRoom(roomId: String): DetailRoomsResponse {
        return apiService.getRoom(roomId)
    }

    suspend fun getBookings(): BookingsResponse {
        val token = getToken()
        return apiService.getBookings("Bearer $token")
    }

    suspend fun createBooking(
        roomId: String,
        startTime: LocalDate,
        endTime: LocalDate,
        purpose: String
    ): Response<Void> {
        val token = getToken()

        return apiService.createBooking( "Bearer $token", roomId, BookingRequest(startTime.toString(),
            endTime.toString(), purpose))
    }

    suspend fun getReviews(roomId: String): ReviewsResponse {
        return apiService.getReviews(roomId)
    }

    suspend fun createReview(roomId: String, comment: String): Response<Void> {
        val token = getToken()
        return apiService.createReview("Bearer $token", roomId, ReviewRequest (comment))
    }

    suspend fun getBookingDetail(bookingId: String): DetailBookingsResponse {

        return apiService.getBookingDetail(bookingId.toString())
    }

    suspend fun updateBooking(bookingId: Int, startTime: String, endTime: String, purpose: String): Response<Void> {

        return apiService.updateBooking(  bookingId.toString(), BookingRequest(startTime, endTime, purpose))
    }

    suspend fun downloadPanduan(): Response<ResponseBody> {
        return apiService.downloadPanduan()
    }




    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}