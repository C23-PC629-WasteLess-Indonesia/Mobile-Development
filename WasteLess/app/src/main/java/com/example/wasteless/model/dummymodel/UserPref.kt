package com.example.wasteless.model.dummymodel

data class UserPref(
    var userId: String? = null,
    var lat: String? = null,
    var lon: String? = null,
    var token: String? = null,
    var isLogin: Boolean = false
)
