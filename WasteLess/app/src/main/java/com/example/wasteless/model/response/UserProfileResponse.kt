package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("fotoProfile")
	val fotoProfile: Any,

	@field:SerializedName("historyDonation")
	val historyDonation: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: Any,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("email")
	val email: String
)
