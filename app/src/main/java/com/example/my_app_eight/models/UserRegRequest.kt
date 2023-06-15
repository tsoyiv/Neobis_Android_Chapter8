package com.example.my_app_eight.models

import com.google.gson.annotations.SerializedName

data class UserRegRequest(
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirm_password")
    val confirm_password: String,
)
