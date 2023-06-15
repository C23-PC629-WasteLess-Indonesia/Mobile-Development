package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

	@field:SerializedName("FoodResponse")
	val foodResponse: List<FoodResponseItem>
)

data class FoodResponseItem(

	@field:SerializedName("expiredAt")
	val expiredAt: String,

	@field:SerializedName("foodName")
	val foodName: String,

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("foodType")
	val foodType: String,

	@field:SerializedName("foodId")
	val foodId: Int,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("fotoMakanan")
	val fotoMakanan: String,

	@field:SerializedName("longitude")
	val longitude: String
)
