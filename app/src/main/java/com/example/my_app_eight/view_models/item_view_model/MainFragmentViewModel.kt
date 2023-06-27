package com.example.my_app_eight.view_models.item_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel : ViewModel() {

    private val _products = MutableLiveData<List<ProductResponse>>()
    val products: LiveData<List<ProductResponse>> = _products

    fun fetchProducts() {
        val productAPI = RetrofitInstance.apiProduct
        val token = Holder.access_token
        val authHolder = "Bearer $token"

        productAPI.getProducts(authHolder).enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: Response<List<ProductResponse>>
            ) {
                if (response.isSuccessful) {
                    val products = response.body()
                    _products.value = products
                } else {
                    // Handle error case
                }
            }
            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                // Handle failure here
            }
        })
    }

}