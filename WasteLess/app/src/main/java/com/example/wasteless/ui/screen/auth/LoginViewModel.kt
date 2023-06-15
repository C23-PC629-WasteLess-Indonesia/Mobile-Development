package com.example.wasteless.ui.screen.auth


import androidx.lifecycle.ViewModel
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.model.response.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val _userAuth = MutableStateFlow<LoginResponse?>(null)
    val userAuth: StateFlow<LoginResponse?> = _userAuth

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    fun loginUser(email: String, password: String){
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _userAuth.value = responseBody
                        _error.value = false
                    }
                }else{
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

        })
    }
}