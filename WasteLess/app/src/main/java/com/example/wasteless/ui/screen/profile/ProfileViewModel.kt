package com.example.wasteless.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.data.UserRepository
import com.example.wasteless.model.dummymodel.Food
import com.example.wasteless.model.dummymodel.History
import com.example.wasteless.model.response.DataItem
import com.example.wasteless.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel: ViewModel() {
    private val _historyFood = MutableStateFlow<List<DataItem>>(listOf())

    val historyFood: StateFlow<List<DataItem>> = _historyFood

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun getFoodByUserId(token: String, id: Int){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getFoodById("Bearer $token", id)
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody != null){
                    _historyFood.value = responseBody.data
                }else{
                    _message.value = "Tidak ada makanan yang didonasikan oleh pengguna ini"
                }
            }
        }
    }
}