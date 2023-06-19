package com.example.my_app_eight.view_models.login_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.models.LoginResponse
import com.example.my_app_eight.models.api.AuthAPI
import com.example.my_app_eight.models.api.RetrofitInstance

class LoginViewModel : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    private val _usernameInput = MutableLiveData<String>()
    val usernameInput: LiveData<String> = _usernameInput

    private val _passwordInput = MutableLiveData<String>()
    val passwordInput: LiveData<String> = _passwordInput

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _isUsernameValid = MutableLiveData<Boolean>()
    val isUsernameValid: LiveData<Boolean> = _isUsernameValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid

    fun onUsernameTextChanged(text: CharSequence?) {
        _usernameInput.value = text?.toString()?.trim()
        updateButtonEnabledState()
    }

    fun onPasswordTextChanged(text: CharSequence?) {
        _passwordInput.value = text?.toString()?.trim()
        updateButtonEnabledState()
    }

    private fun updateButtonEnabledState() {
        val username = _usernameInput.value
        val password = _passwordInput.value

        _isButtonEnabled.value = !username.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}