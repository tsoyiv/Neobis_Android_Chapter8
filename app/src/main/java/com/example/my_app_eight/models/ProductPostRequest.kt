package com.example.my_app_eight.models

import android.net.Uri

data class ProductPostRequest(
    val name: String,
    val price: String,
    val description: String,
    val photo: Uri?
)
