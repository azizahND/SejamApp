package com.example.sejam.data.response

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
	val room: Room
)

data class ReviewsItem(
	val createdAt: String,
	@SerializedName("User")
	val user: User,
	val comment: String,
	val id: Int,
	val userId: Int,
	val roomId: Int,
	val updatedAt: String
)

data class Room(
	val createdAt: String,
	@SerializedName("Reviews")
	val reviews: List<ReviewsItem>,
	val name: String,
	val description: String,
	val id: Int,
	val image: String,
	val facilities: String,
	val capacity: Int,
	val status: String,
	val updatedAt: String
)