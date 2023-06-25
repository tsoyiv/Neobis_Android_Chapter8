package com.example.my_app_eight.models.api

import com.example.my_app_eight.models.SendVerificationCodeRequest
import com.example.my_app_eight.models.UserInfoRequest
import com.example.my_app_eight.models.VerificationCodeResponse
import com.example.my_app_eight.models.VerifyCodeRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserInfoAPI {
    @PUT("profile/")
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body userInfo: UserInfoRequest
    ): Response<Any>

    @PUT("send_verification_code/")
    fun sendVerificationCode(
        @Header("Authorization") token: String,
        @Body request: SendVerificationCodeRequest
    ): Call<VerificationCodeResponse>

    @POST("verify_phone/")
    fun verifyCode(@Body request: VerifyCodeRequest): Response<ResponseBody>
}