package com.example.my_app_eight.models

import okhttp3.MultipartBody

data class ProductPostRequest(
    val name: String,
    val price: String,
    val short_description: String,
    val full_description: String,
    val photo: List<MultipartBody.Part>
)
