package com.example.my_app_eight.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = getAccessTokenFromSharedPreferences()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(request)
    }

    private fun getAccessTokenFromSharedPreferences(): String {
        val sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", "") ?: ""
    }
}

//class TokenManager(private val context: Context) {
//    private val sharedPreferences: SharedPreferences =
//        context.getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE)
//    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//    fun saveToken(accessToken: String) {
//        editor.putString("access_token", accessToken)
//        editor.apply()
//    }
//
//    fun getToken(): String? {
//        return sharedPreferences.getString("access_token", null)
//    }
//
//    fun clearToken() {
//        editor.remove("access_token")
//        editor.apply()
//    }
//}