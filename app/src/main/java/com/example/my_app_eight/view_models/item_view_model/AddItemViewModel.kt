package com.example.my_app_eight.view_models.item_view_model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.ProductPostRequest
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddItemViewModel : ViewModel() {
    private val _itemAddedSuccess = MutableLiveData<Boolean>()
    val itemAddedSuccess: LiveData<Boolean> = _itemAddedSuccess

    fun inputItem(
        name: String,
        price: String,
        description: String,
        photo: Uri?
    ) {
        val item = ProductPostRequest(
            name = name,
            price = price,
            description = description,
            photo = photo
        )
        postItemToServer(item)
    }

    private fun postItemToServer(item: ProductPostRequest) {
        val apiService = RetrofitInstance.apiProduct

        val token = Holder.access_token
        val authH = "Bearer $token"
        apiService.addItem(authH, item).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                _itemAddedSuccess.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                // Handle failure here
            }
        })
    }
}
