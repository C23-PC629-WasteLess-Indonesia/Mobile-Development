package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class PostFoodResponse(

	@field:SerializedName("message")
	val message: String
)
