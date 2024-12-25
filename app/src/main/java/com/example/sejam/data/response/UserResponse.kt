package com.example.sejam.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User
)

