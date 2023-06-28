package com.example.my_app_eight.fragments.usage_fragments.item_setting_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentDescBinding
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescFragment : Fragment() {

    val args: DescFragmentArgs by navArgs()
    private lateinit var product: ProductResponse
    private lateinit var binding : FragmentDescBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()

        toDescPage()
        returnToMainPage()
    }

    private fun returnToMainPage() {
        binding.returnFromDesc.setOnClickListener {
            findNavController().navigate(R.id.action_descFragment_to_mainFragment)
        }
    }

    private fun toDescPage() {
        product = args.itemDesc
        val token = Holder.access_token
        val authHolder = "Bearer $token"
        fetchProductDetails(product.id, authHolder)
        updateUI(product)
    }

    private fun fetchProductDetails(productId: Int, token: String) {
        val apiService = RetrofitInstance.apiProduct

        apiService.getProductDetails(productId, token).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {

                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                // Handle network failure
            }
        })
    }

    private fun updateUI(product: ProductResponse) {
        binding.itemPrice.text = product.price
        binding.numbOfLike.text = product.like_count
        binding.nameOfItem.text = product.name
        binding.shortDesc.text = product.description
    }
}