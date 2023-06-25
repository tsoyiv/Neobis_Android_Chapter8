package com.example.my_app_eight.models.api

import com.example.my_app_eight.models.SendVerificationCodeRequest
import com.example.my_app_eight.models.UserInfoRequest
import com.example.my_app_eight.models.VerifyCodeRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserInfoAPI {
//    @PUT("profile/")
//    fun updateUserInfo(@Body userInfo: UserInfo): Call<Void>
    @PUT("profile/")  // Replace "/user" with your actual endpoint
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body userInfo: UserInfoRequest
    ): Response<Any>

    @PUT("send_verification_code/")
    fun sendVerificationCode(@Body request: SendVerificationCodeRequest): Response<ResponseBody>

    @POST("verify_phone/")
    fun verifyCode(@Body request: VerifyCodeRequest): Response<ResponseBody>
}