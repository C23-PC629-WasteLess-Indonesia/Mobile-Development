package com.example.wasteless.ui.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.data.UserRepository
import com.example.wasteless.model.dummymodel.User
import com.example.wasteless.model.response.UserProfileResponse
import com.example.wasteless.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UserProfileResponse>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<UserProfileResponse>>
        get() = _uiState

    fun getUserData(token: String){
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getUserProfile("Bearer $token")
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody != null){
                    _uiState.value = UiState.Success(responseBody)
                }else{
                    _uiState.value = UiState.Error("Tidak ada data")
                }
            }else{
                _uiState.value = UiState.Error(response.message())
            }
        }
    }


}