package com.example.my_app_eight.models.api

import com.example.my_app_eight.models.LoginRequest
import com.example.my_app_eight.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}