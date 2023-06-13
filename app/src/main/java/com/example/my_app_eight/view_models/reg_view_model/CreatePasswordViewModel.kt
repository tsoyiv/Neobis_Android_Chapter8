package com.example.my_app_eight.view_models.reg_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatePasswordViewModel : ViewModel() {

    private val _passwordInput = MutableLiveData<String>()
    private val _confirmPasswordInput = MutableLiveData<String>()
    private val _isButtonEnabled = MutableLiveData<Boolean>()

    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    init {
        _isButtonEnabled.value = false
    }

    fun onPasswordTextChanged(text: CharSequence?) {
        _passwordInput.value = text?.toString()?.trim()
        validatePasswords()
    }

    fun onConfirmPasswordTextChanged(text: CharSequence?) {
        _confirmPasswordInput.value = text?.toString()?.trim()
        validatePasswords()
    }

    private fun validatePasswords() {
        val password = _passwordInput.value
        val confirmPassword = _confirmPasswordInput.value

        val isPasswordsValid = isPasswordsMatching(password, confirmPassword)
                && isPasswordLengthValid(password)
                && isPasswordContainingNumber(password)

        _isButtonEnabled.value = isPasswordsValid
    }

    private fun isPasswordsMatching(password: String?, confirmPassword: String?): Boolean {
        return password == confirmPassword
    }

    private fun isPasswordLengthValid(password: String?): Boolean {
        return (password?.length ?: 0) >= 8
    }

    private fun isPasswordContainingNumber(password: String?): Boolean {
        return password?.contains(Regex(".*\\d.*")) ?: false
    }
}