package com.example.my_app_eight.api

import com.example.my_app_eight.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserDataAPI {
    @Multipart
    @PUT("profile/")
    fun updateUserInfo(
        @Header("Authorization") token: String,
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("username") username: RequestBody,
        @Part("date_of_birth") dateOfBirth: RequestBody,
        @Part photo: List<MultipartBody.Part>
    ): Call<Any>

    @PUT("send_verification_code/")
    fun sendVerificationCode(
        @Header("Authorization") token: String,
        @Body request: SendVerificationCodeRequest
    ): Call<VerificationCodeResponse>

    @POST("verify_phone/")
    fun verifyCode(
        @Body request: VerifyCodeRequest
    ): Response<ResponseBody>
}