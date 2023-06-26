package com.example.my_app_eight.view_models.profile_view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.UserDataRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileDataViewModel : ViewModel() {
    var name: String = ""
    var surname: String = ""
    var nickname: String = ""
    var birthday: String = ""
    var number: String = ""

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    fun updateUser(accessToken: String, userInfo: UserDataRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val authHeader = "Bearer $accessToken"
                val response = RetrofitInstance.apiUser.updateUserInfo(authHeader, userInfo)
                if (response.isSuccessful) {
                    _updateResult.postValue(true)
                } else {
                    _updateResult.postValue(false)
                }
            } catch (e: HttpException) {
                // Handle HTTP exception
            } catch (e: Exception) {
                // Handle other exceptions
            }
        }
    }
}