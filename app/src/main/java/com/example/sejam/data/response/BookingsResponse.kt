package com.example.sejam.data.response

import com.google.gson.annotations.SerializedName

data class BookingsResponse(

	@field:SerializedName("bookings")
	val bookings: List<MyBookingsItem>
)

data class MyBookingsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("purpose")
	val purpose: Any,

	@field:SerializedName("startTime")
	val startTime: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("endTime")
	val endTime: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("Room")
	val room: Room,

	@field:SerializedName("roomId")
	val roomId: Int,

	@field:SerializedName("documentPath")
	val documentPath: Any,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

