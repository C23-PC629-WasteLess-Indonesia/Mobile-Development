package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
)
