package com.example.wasteless.ui.screen.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.model.response.RegisterResponse
import com.example.wasteless.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    private val _error = MutableStateFlow(false)
    val error:StateFlow<Boolean> = _error

    fun registerUser(name: String, email: String, password: String){
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _error.value = false
                    }
                }else{
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }

        })
    }
}