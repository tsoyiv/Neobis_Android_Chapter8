package com.example.my_app_eight.view_models.login_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_eight.api.AuthAPI
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.LoginRequest
import com.example.my_app_eight.models.LoginResponse
import com.example.my_app_eight.util.Holder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val authAPI: AuthAPI = RetrofitInstance.apiAuth

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

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _loginError = MutableLiveData<Unit>()
    val loginError: LiveData<Unit> get() = _loginError

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

    fun login(username: String, password: String) {
        val request = LoginRequest(username, password)
        viewModelScope.launch {
            try {
                val response: Response<LoginResponse> = withContext(Dispatchers.IO) {
                    authAPI.login(request)
                }
                if (response.isSuccessful) {
                    Holder.username = username

                    val loginResponse = response.body()
                    val accessToken = loginResponse?.access
                    val refreshToken = loginResponse?.refresh
                    if (refreshToken != null && accessToken != null) {
                        Holder.access_token = accessToken
                    }
                    _loginSuccess.value = true
                } else {
                    _loginSuccess.value = false
                }
            } catch (t: Throwable) {
                _loginError.value = Unit
            }
        }
    }

}