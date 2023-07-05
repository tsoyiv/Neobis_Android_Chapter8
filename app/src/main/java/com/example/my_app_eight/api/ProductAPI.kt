package com.example.my_app_eight.api

import com.example.my_app_eight.models.FavoriteRequest
import com.example.my_app_eight.models.FavoriteResponse
import com.example.my_app_eight.models.ProductPostRequest
import com.example.my_app_eight.models.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {
    @Multipart
    @POST("products/")
    fun productCreate(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("full_description") fullDescription: RequestBody,
        @Part("short_description") shortDescription: RequestBody,
        @Part photo: List<MultipartBody.Part>
    ): Call<ProductResponse>

    @GET("products/")
    fun getProducts(
        @Header("Authorization") token: String
    ): Call<List<ProductResponse>>

    @Multipart
    @PUT("products/{id}/")
    fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part("full_description") fullDescription: RequestBody,
        @Part("short_description") shortDescription: RequestBody,
        @Part photo: List<MultipartBody.Part>
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