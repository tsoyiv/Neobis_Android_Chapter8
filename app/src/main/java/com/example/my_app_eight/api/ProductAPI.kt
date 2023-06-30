package com.example.my_app_eight.api

import com.example.my_app_eight.models.FavoriteRequest
import com.example.my_app_eight.models.FavoriteResponse
import com.example.my_app_eight.models.ProductPostRequest
import com.example.my_app_eight.models.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {
    @POST("products/")
    fun addItem(
        @Header("Authorization") token: String,
        @Body product: ProductPostRequest
    ): Call<ProductResponse>

//    @POST("products/")
//    fun addItem(
//        @Header("Authorization") token: String,
//        @Part("name") name: ProductPostRequest,
//        @Part("price") price: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("photo") photo: MultipartBody.Part,
//        @Body product: ProductPostRequest
//    ): Call<ProductResponse>

    @GET("products/")
    fun getProducts(
        @Header("Authorization") token: String
    ): Call<List<ProductResponse>>

    @PUT("products/{id}/")
    fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body product: ProductPostRequest
    ): Call<ProductResponse>

    @DELETE("products/{id}/")
    fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<Void>

    @POST("favorites/")
    fun addToFavorites(
        @Header("Authorization") token: String,
        @Body request: FavoriteRequest
    ): Call<Unit>

    @GET("favorites/")
    fun getFavorites(
        @Header("Authorization") token: String
    ): Call<List<FavoriteResponse>>

    @DELETE("favorites/{id}/")
    fun unlike(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<Void>

    @GET("products/{id}/")
    fun getProductDetails(
        @Path("id") productId: Int,
        @Header("Authorization") token: String
    ): Call<ProductResponse>
}