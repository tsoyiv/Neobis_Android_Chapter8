package com.example.my_app_eight.view_models.item_view_model

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.api.ProductAPI
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.ImageConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class AddItemViewModel : ViewModel() {

    private val _itemAddedSuccess = MutableLiveData<Boolean>()
    val itemAddedSuccess: LiveData<Boolean> = _itemAddedSuccess

    fun inputItem(
        context: Context,
        token: String,
        name: String,
        price: String,
        short_description: String,
        full_description: String,
        imageUri: Uri,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val apiInterface = RetrofitInstance.apiProduct

        val nameSide = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceSide = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val shortDescriptionSide = short_description.toRequestBody("text/plain".toMediaTypeOrNull())
        val fullDescriptionSide = full_description.toRequestBody("text/plain".toMediaTypeOrNull())

        val imageFile = ImageConverter.getFile(context, imageUri)
        if (imageFile != null) {
            val imagePart = prepareImagePart(imageFile)
            val imageParts = listOf(imagePart)

            apiInterface.productCreate(
                token = token,
                name = nameSide,
                price = priceSide,
                fullDescription = fullDescriptionSide,
                shortDescription = shortDescriptionSide,
                photo = imageParts
            ).enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError()
                    }
                }
                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    onError()
                }
            })
        } else {
            onError()
        }
    }

    private fun prepareImagePart(imageFile: File): MultipartBody.Part {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("photo", imageFile.name, requestFile)
    }
}

//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.my_app_eight.api.RetrofitInstance
//import com.example.my_app_eight.models.ProductResponse
//import com.example.my_app_eight.util.Holder
//import com.example.my_app_eight.util.ImageConverter
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.File
//
//class AddItemViewModel : ViewModel() {
//    private val _itemAddedSuccess = MutableLiveData<Boolean>()
//    val itemAddedSuccess: LiveData<Boolean> = _itemAddedSuccess
//
//    fun inputItem(
//        context: Context,
//        token: String,
//        name: RequestBody,
//        price: RequestBody,
//        short_description: RequestBody,
//        full_description: RequestBody,
//        //images: List<MultipartBody.Part>,
//        onSuccess: () -> Unit,
//        onError: () -> Unit
//    ) {
//        val apiInterface = RetrofitInstance.apiProduct
//
//        val images = Holder.imageList
//        val imageParts = mutableListOf<MultipartBody.Part>()
//        images.forEachIndexed { _, image ->
//            val file: File? = ImageConverter.getFile(context, image)
//            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
//            val imageInst = requestBody?.let {
//                MultipartBody.Part.createFormData("images", file.name, it)
//            }
//            imageInst?.let { imageParts.add(it) }
//        }
//


//        for (image in photos) {
//            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), image.toFile())
//            val imagePart = MultipartBody.Part.createFormData("images[]", image.name, requestFile)
//            imageInstr.add(imagePart)
//        }

//        val nameSide = name.toRequestBody("text/plain".toMediaTypeOrNull())
//        val priceSide = price.toRequestBody("text/plain".toMediaTypeOrNull())
//        val descriptionSide = description.toRequestBody("text/plain".toMediaTypeOrNull())
//
//        apiInterface.productCreate(
//            token = token,
//            name = name,
//            price = price,
//            short_description = short_description,
//            full_description = full_description,
//            photo = imageParts
//        ).enqueue(object : Callback<ProductResponse> {
//            override fun onResponse(
//                call: Call<ProductResponse>,
//                response: Response<ProductResponse>
//            ) {
//                if (response.isSuccessful) {
//                    onSuccess()
//                    Log.d("Statusssss", "Sent")
//                } else {
//                    onError()
//                    Log.d("Statusssss", "Failed")
//                }
//            }
//
//            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                onError()
//            }
//
//        })
//
//    }
//    fun inputItem(
//        fragment : Fragment,
//        name: String,
//        price: String,
//        description: String,
//        photo: MutableList<String>?
//    ) {
//        val images = Holder.imageList
//
//        val imageParts = mutableListOf<MultipartBody.Part>()
//
//        images.forEachIndexed { index, image ->
//            val file: File? = ImageConverter.getFile(fragment.requireContext(), image)
//            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
//            val imagePart = requestBody?.let {
//                MultipartBody.Part.createFormData("images", file.name, it)
//            }
//            imagePart?.let { imageParts.add(it) }
//        }
//
//        val item = ProductPostRequest(
//            name = name,
//            price = price,
//            description = description,
//            photo = imageParts
//        )
//        postItemToServer(item)
//    }
//
//    private fun postItemToServer(item: ProductPostRequest) {
//        val apiService = RetrofitInstance.apiProduct
//
//        val token = Holder.access_token
//        val authH = "Bearer $token"
//        apiService.addItem(authH, item).enqueue(object : Callback<ProductResponse> {
//            override fun onResponse(
//                call: Call<ProductResponse>,
//                response: Response<ProductResponse>
//            ) {
//                _itemAddedSuccess.value = response.isSuccessful
//            }
//
//            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                // Handle failure here
//            }
//        })
//    }
