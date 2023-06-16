package com.example.my_app_eight.models

import com.google.gson.annotations.SerializedName

data class UserRegRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirm_password")
    val confirm_password: String,
)
