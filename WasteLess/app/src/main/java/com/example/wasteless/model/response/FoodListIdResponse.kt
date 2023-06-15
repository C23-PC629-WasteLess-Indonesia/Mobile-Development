package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class FoodListIdResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: Any
)

data class DataItem(

	@field:SerializedName("expiredAt")
	val expiredAt: String,

	@field:SerializedName("foodId")
	val foodId: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("fotoMakanan")
	val fotoMakanan: String
)
