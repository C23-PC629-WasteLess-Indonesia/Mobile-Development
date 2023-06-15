package com.example.wasteless.ui.screen.home

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.api.ApiService
import com.example.wasteless.data.FoodRepository
import com.example.wasteless.model.response.*
import com.example.wasteless.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomeViewModel : ViewModel() {
    private val _foods = MutableStateFlow<List<PreferenceFoodResponseItem>>(listOf())

    val foods: StateFlow<List<PreferenceFoodResponseItem>> = _foods

    private val _allFoods = MutableStateFlow<List<AllFoodListResponseItem>>(listOf())

    val allFoods: StateFlow<List<AllFoodListResponseItem>> = _allFoods

    fun getFoodsPreference(token: String){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getPreferences("Bearer $token")
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody != null){
                    _foods.value = responseBody
                }
            }
        }
    }
    fun getAllFoods(token: String){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getAllFoodList("Bearer $token")
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody != null){
                    _allFoods.value = responseBody
                }
            }
        }
    }
}
