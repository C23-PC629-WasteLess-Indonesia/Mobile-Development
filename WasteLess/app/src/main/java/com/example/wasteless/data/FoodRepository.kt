package com.example.wasteless.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.model.response.FoodResponse
import com.example.wasteless.model.response.FoodResponseItem
import com.example.wasteless.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRepository {


    companion object{
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this){
                FoodRepository().apply {
                    instance = this
                }
            }
    }
}