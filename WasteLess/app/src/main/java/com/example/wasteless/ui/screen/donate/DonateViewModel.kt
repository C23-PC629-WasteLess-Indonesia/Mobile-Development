package com.example.wasteless.ui.screen.donate

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.FileUtils
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wasteless.api.ApiConfig
import com.example.wasteless.api.ApiService
import com.example.wasteless.data.FoodRepository
import com.example.wasteless.model.response.PostFoodResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.Okio
import okio.source
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.net.URI
import kotlin.math.sin

class DonateViewModel: ViewModel() {
    private val _responsePost = MutableStateFlow<String>("")

    val responsePost: StateFlow<String> = _responsePost

    fun postFood(
        context: Context,
        token: String,
        uri: Uri,
        namaMakanan: String,
        lokasiMakanan: String,
        deskripsiMakanan: String,
        jumlahMakanan: String,
        kategori: String,
        kadaluarsa: String,
    ){

        val contentResolver: ContentResolver = context.contentResolver
            val requestFile = object : RequestBody(){
                override fun contentType(): MediaType? {
                    return contentResolver.getType(uri)?.toMediaTypeOrNull()
                }

                override fun writeTo(sink: BufferedSink) {
                    contentResolver.openInputStream(uri)?.use { input ->
                        input.source().use { source ->
                            sink.writeAll(source)
                        }
                    }
                }

            }
        val filePart = MultipartBody.Part.createFormData("fotoMakanan",uri.lastPathSegment, requestFile)
        val foodName = namaMakanan.toRequestBody("text/plain".toMediaType())
        val location = lokasiMakanan.toRequestBody("text/plain".toMediaType())
        val description = deskripsiMakanan.toRequestBody("text/plain".toMediaType())
        val quantity = jumlahMakanan.toRequestBody("text/plain".toMediaType())
        val foodType = kategori.toRequestBody("text/plain".toMediaType())
        val expiredAt = kadaluarsa.toRequestBody("text/plain".toMediaType())

        viewModelScope.launch {
            val client = ApiConfig.getApiService().uploadFood(
                "Bearer $token",
                filePart,
                foodName,
                location,
                description,
                quantity,
                foodType,
                expiredAt
            )
            val response = withContext(Dispatchers.IO){
                client.execute()
            }
            if (response.isSuccessful){
                _responsePost.value = response.message()
            }
        }
    }
}