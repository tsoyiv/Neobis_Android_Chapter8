package com.example.my_app_eight.models.api

import com.example.my_app_eight.models.LoginRequest
import com.example.my_app_eight.models.LoginResponse
import com.example.my_app_eight.models.UserInfoRequest
import com.example.my_app_eight.models.UserRegRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthAPI : UserInfoAPI {
    @POST("login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register/")
    fun registerUser(@Body request: UserRegRequest): Call<Unit>
}