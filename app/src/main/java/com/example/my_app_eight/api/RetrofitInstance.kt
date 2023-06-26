package com.example.my_app_eight.api

import com.example.my_app_eight.util.TokenInterceptor
import com.example.my_app_eight.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(TokenInterceptor())
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val apiAuth by lazy {
            retrofit.create(AuthAPI::class.java)
        }
        val apiUser by lazy {
            retrofit.create(UserDataAPI::class.java)
        }
        val apiProduct by lazy {
            retrofit.create(ProductAPI::class.java)
        }
    }
}
