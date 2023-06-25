package com.example.my_app_eight.view_models.profile_view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.VerifyCodeRequest

class CodeViewModel : ViewModel() {

    private val _isCodeVerified = MutableLiveData<Boolean>()
    val isCodeVerified: LiveData<Boolean> get() = _isCodeVerified

    fun verifyCode(code: String) {
        val verifyCodeRequest = VerifyCodeRequest(code)

        try {
            val response = RetrofitInstance.apiUser.verifyCode(verifyCodeRequest)
            _isCodeVerified.value = response.isSuccessful
        } catch (e: Exception) {
            _isCodeVerified.value = false
        }
    }
}