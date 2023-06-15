package com.example.wasteless.ui.screen.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.data.FoodRepository
import com.example.wasteless.model.dummymodel.Food
import com.example.wasteless.model.response.FoodResponseItem
import com.example.wasteless.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreViewModel: ViewModel() {
    private val _allFoods = MutableStateFlow<List<FoodResponseItem>>(listOf())

    val allFoods: StateFlow<List<FoodResponseItem>> = _allFoods

    fun getAllFoods(token: String){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().foods("Bearer $token")
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