package com.example.my_app_eight.view_models.item_view_model

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.ImageConverter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateViewModel : ViewModel() {
    private val _itemAddedSuccess = MutableLiveData<Boolean>()
    val itemAddedSuccess: LiveData<Boolean> = _itemAddedSuccess

    fun inputItem(
        context: Context,
        token: String,
        id: Int,
        name: String,
        price: String,
        short_description: String,
        full_description: String,
        imageUri: Uri
    ) {
        val apiInterface = RetrofitInstance.apiProduct

        val nameRequest = name.toRequestBody()
        val priceRequest = price.toRequestBody()
        val shortDescriptionRequest = short_description.toRequestBody()
        val fullDescriptionRequest = full_description.toRequestBody()

        val imageFile = ImageConverter.getFile(context, imageUri)
        if (imageFile != null) {
            val imagePart = prepareImagePart(imageFile)
            val imageParts = listOf(imagePart)

            apiInterface.updateProduct(
                token = token,
                id = id,
                name = nameRequest,
                price = priceRequest,
                fullDescription = fullDescriptionRequest,
                shortDescription = shortDescriptionRequest,
                photo = imageParts
            ).enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        _itemAddedSuccess.value = true
                    } else {
                        _itemAddedSuccess.value = false
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
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
