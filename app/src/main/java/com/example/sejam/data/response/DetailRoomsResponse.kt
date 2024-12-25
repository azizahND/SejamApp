package com.example.sejam.data.response

import com.google.gson.annotations.SerializedName

data class DetailRoomsResponse(

	@field:SerializedName("data")
	val data: DetailRoomsData,

	@field:SerializedName("status")
	val status: String
)

data class DetailRoomsData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("totalReviews")
	val totalReviews: Int,

	@field:SerializedName("averageRating")
	val averageRating: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("Bookings")
	val bookings: List<BookingsItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("facilities")
	val facilities: String,

	@field:SerializedName("capacity")
	val capacity: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("image")
	val image: String,
)

data class BookingsItem(

	@field:SerializedName("User")
	val user: User,

	@field:SerializedName("purpose")
	val purpose: Any,

	@field:SerializedName("startTime")
	val startTime: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("endTime")
	val endTime: String,

	@field:SerializedName("status")
	val status: String
)

