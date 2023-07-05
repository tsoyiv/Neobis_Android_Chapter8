package com.example.my_app_eight.view_models.profile_view_models

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.util.ImageConverter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileDataViewModel : ViewModel() {
    var name: String = ""
    var surname: String = ""
    var nickname: String = ""
    var birthday: String = ""
    var number: String = ""

    private val _itemAddedSuccess = MutableLiveData<Boolean>()
    val itemUpdatedSuccess: LiveData<Boolean> = _itemAddedSuccess

    fun inputItem(
        context: Context,
        token: String,
        first_name: String,
        last_name: String,
        username: String,
        date_of_birth: String,
        imageUri: Uri
    ) {
        val apiInterface = RetrofitInstance.apiUser

        val firstNameRequest = first_name.toRequestBody()
        val lastNameRequest = last_name.toRequestBody()
        val usernameRequest = username.toRequestBody()
        val dateRequest = date_of_birth.toRequestBody()

        val imageFile = ImageConverter.getFile(context, imageUri)
        if (imageFile != null) {
            val imagePart = prepareImagePart(imageFile)
            val imageParts = listOf(imagePart)

            apiInterface.updateUserInfo(
                token = token,
                firstName = firstNameRequest,
                lastName = lastNameRequest,
                username = usernameRequest,
                dateOfBirth = dateRequest,
                photo = imageParts
            ).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        _itemAddedSuccess.value = true
                    } else {
                        _itemAddedSuccess.value = false
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    _itemAddedSuccess.value = false
                }
            })

        }
    }

    private fun prepareImagePart(imageFile: File): MultipartBody.Part {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("photo", imageFile.name, requestFile)
    }
}