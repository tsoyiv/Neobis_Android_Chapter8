package com.example.my_app_eight.api

import com.example.my_app_eight.models.ProductPostRequest
import com.example.my_app_eight.models.ProductResponse
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {
    @POST("products/")
    fun addItem(
        @Header("Authorization") token: String,
        @Body product: ProductPostRequest
    ): Call<ProductResponse>

    @GET("products/")
    fun getProducts(@Header("Authorization") token: String): Call<List<ProductResponse>>

//    @DELETE("products/{id}/")
//    fun deleteProduct(
//        @Header("Authorization") token: String,
//        @Path("id") productId: ProductResponse
//    ): Call<Void>

    @DELETE("products/{id}/")
    fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<Void>
}