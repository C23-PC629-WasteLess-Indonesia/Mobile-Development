package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class AllFoodListResponse(

	@field:SerializedName("AllFoodListResponse")
	val allFoodListResponse: List<AllFoodListResponseItem>
)

data class AllFoodListResponseItem(

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

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("fotoMakanan")
	val fotoMakanan: String,

	@field:SerializedName("longitude")
	val longitude: String
)
