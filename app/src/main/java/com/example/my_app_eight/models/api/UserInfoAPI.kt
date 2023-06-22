package com.example.my_app_eight.models.api

import com.example.my_app_eight.models.UserInfoRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserInfoAPI {
//    @PUT("profile/")
//    fun updateUserInfo(@Body userInfo: UserInfo): Call<Void>
    @PUT("profile/")  // Replace "/user" with your actual endpoint
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body userInfo: UserInfoRequest
    ): Response<Any>

}