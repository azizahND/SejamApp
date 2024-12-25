package com.example.sejam.data.response

import com.google.gson.annotations.SerializedName

data class RoomsResponse(

	@field:SerializedName("data")
	val data: List<DataItemRooms>,

	@field:SerializedName("status")
	val status: String
)

data class RoomResponse(

	@field:SerializedName("data")
	val data: DataItemRooms,

	@field:SerializedName("status")
	val status: String
)


data class DataItemRooms(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("totalReviews")
	val totalReviews: Int,

	@field:SerializedName("averageRating")
	val averageRating: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("facilities")
	val facilities: String,

	@field:SerializedName("capacity")
	val capacity: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
