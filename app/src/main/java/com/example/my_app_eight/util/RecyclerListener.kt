package com.example.my_app_eight.util

import com.example.my_app_eight.models.ProductResponse

interface RecyclerListener {
    fun deleteProduct(productId: Int)
    //fun updateProduct(productId: Int, product: ProductResponse)
    fun likeProduct(productId: Int)
    //fun unLike(productId: Int)
}