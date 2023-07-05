package com.example.my_app_eight.models

data class ProductResponse(
    val id: Int,
    val like_count: String,
    val name: String,
    val price: String,
    val photo: String,
    val full_description: String,
    val short_description: String,
    val owner: Int
) : java.io.Serializable
