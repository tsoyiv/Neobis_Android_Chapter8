package com.example.my_app_eight.models

data class ProductResponse(
    val id: Int,
    val name: String,
    val price: String,
    val description: String,
    val photo: String,
    val owner: Int,
    val likes: List<Like>
)
data class Like(
    val username: String
)
