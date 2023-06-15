package com.example.wasteless.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.data.FoodRepository
import com.example.wasteless.model.dummymodel.Food
import com.example.wasteless.model.response.FoodDetailResponse
import com.example.wasteless.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(): ViewModel() {
    private val _detailFood = MutableStateFlow<FoodDetailResponse?>(null)
    val detailFood: StateFlow<FoodDetailResponse?> = _detailFood

    private val _history = MutableStateFlow("")
    val history: StateFlow<String> = _history

    fun getFoodById(token:String, id: Int){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().detailFood("Bearer $token", id)
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody != null){
                    _detailFood.value = responseBody
                }
            }
        }
    }
    fun createHistory(token: String, userIdPeminat: Int, foodId: Int, userIdDonatur: Int, status: Boolean){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().createHistory("Bearer $token",userIdPeminat, foodId, userIdDonatur, status)
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                _history.value = response.body()?.message ?: "Berhasil Request"
            }
        }
    }
}