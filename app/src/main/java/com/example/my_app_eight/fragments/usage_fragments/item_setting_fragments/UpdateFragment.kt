package com.example.my_app_eight.fragments.usage_fragments.item_setting_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentUpdateBinding
import com.example.my_app_eight.models.ProductPostRequest
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import kotlinx.android.synthetic.main.fragment_update.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    val args: UpdateFragmentArgs by navArgs()
    private lateinit var product: ProductResponse
    private val imageList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = args.updateArgs
        setPrevInfoAboutProduct(product)

        cancelBtn()
        updateItem()
    }

    private fun cancelBtn() {
        binding.btnCancelUpdateItem.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_userItemsFragment)
        }
    }

    private fun updateItem() {
        binding.btnReadyUpdateItem.setOnClickListener {
            toUpdatePage()
        }
    }

    private fun toUpdatePage() {
        val token = Holder.access_token
        val authHolder = "Bearer $token"
        fetchProductDetails(product.id, authHolder)
    }

    private fun fetchProductDetails(productId: Int, token: String) {
        val apiService = RetrofitInstance.apiProduct

        apiService.getProductDetails(productId, token).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val productDetails = response.body()
                    if (productDetails != null) {
                        updateItem(productDetails.id, token)
                    } else {
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                // Handle network failure
            }
        })
    }

    private fun updateItem(item: Int, token: String) {
        val updatedProductImages: List<MultipartBody.Part> = imageList.map { imagePath ->
            val file = File(imagePath)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        }
        val apiService = RetrofitInstance.apiProduct

        val updatedProduct = ProductPostRequest(
            updateText_name.text.toString(),
            updateText_price.text.toString(),
            updateText_shortD.text.toString(),
            updateText_longD.text.toString(),
            updatedProductImages
        )
        apiService.updateProduct(token, item, updatedProduct)
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_updateFragment_to_userItemsFragment)
                    } else {
                        Toast.makeText(requireContext(), "Error happened", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    // Handle failure
                }
            })
    }

    private fun setPrevInfoAboutProduct(product: ProductResponse) {
        binding.updateTextPrice.setText(product.price)
        binding.updateTextName.setText(product.name)
        binding.updateTextShortD.setText(product.short_description)
        binding.updateTextLongD.setText(product.full_description)
    }
}