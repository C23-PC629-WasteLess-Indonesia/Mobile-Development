package com.example.wasteless.api

import com.example.wasteless.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("foodList")
    fun foods(
        @Header("Authorization") token: String,
    ): Call<ArrayList<FoodResponseItem>>

    @GET("foodDetail/{id}")
    fun detailFood(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<FoodDetailResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("userProfile")
    fun getUserProfile(
        @Header("Authorization") token: String,
    ): Call<UserProfileResponse>

    @Multipart
    @POST("postFood")
    fun uploadFood(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("foodName") foodName: RequestBody,
        @Part("location") location: RequestBody,
        @Part("description") description: RequestBody,
        @Part("quantity") quantity: RequestBody,
        @Part("foodType") foodType: RequestBody,
        @Part("expiredAt") expiredAt: RequestBody,
    ): Call<PostFoodResponse>

    @GET("allFoodList")
    fun getAllFoodList(
        @Header("Authorization") token: String,
    ): Call<ArrayList<AllFoodListResponseItem>>

    @GET("preferancefoodList")
    fun getPreferences(
        @Header("Authorization") token: String,
    ): Call<ArrayList<PreferenceFoodResponseItem>>

    @FormUrlEncoded
    @POST("history")
    fun createHistory(
        @Header("Authorization") token: String,
        @Field("userId_peminat") userId_peminat: Int,
        @Field("foodId") foodId: Int,
        @Field("userId_donatur") userIdDonatur: Int,
        @Field("status") status: Boolean,
    ): Call<CreateHistoryResponse>

    @GET("foodList/{id}")
    fun getFoodById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<FoodListIdResponse>
}