package com.example.my_app_eight.view_models.profile_view_models

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NumbViewModel : ViewModel() {
    val phoneNumber = ObservableField<String>()

    private val _passwordInput = MutableLiveData<String>()
    val passwordInput: LiveData<String> = _passwordInput

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid

    fun onPasswordTextChanged(text: CharSequence?) {
        _passwordInput.value = text?.toString()?.trim()
        updateButtonEnabledState()
    }

    private fun updateButtonEnabledState() {
        val number = _passwordInput.value
        _isButtonEnabled.value = !number.isNullOrEmpty()
    }
}