package com.example.wasteless.model.dummymodel

data class Food(
    val id: Int,
    val nama: String,
    val deskripsi: String,
    val jumlah: String,
    val kadaluasra: String,
    val foto: Int,
    val lokasi: String,
    val lat: Double,
    val lon: Double,
    val user: User,
    val tanggal_post: String,
    val category: String,
    val status: Boolean
)
