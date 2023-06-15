package com.example.wasteless.model.dummymodel

data class FoodCategory(
    val id: Int,
    val categoryName: String,
)

val listCategory = listOf(
    FoodCategory(
        1,
        "vegetarian"
    ),
    FoodCategory(
        2,
        "non-vegetarian"
    )
)