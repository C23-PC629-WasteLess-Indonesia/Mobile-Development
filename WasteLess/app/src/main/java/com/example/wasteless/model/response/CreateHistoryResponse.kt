package com.example.wasteless.model.response

import com.google.gson.annotations.SerializedName

data class CreateHistoryResponse(

	@field:SerializedName("message")
	val message: String
)
