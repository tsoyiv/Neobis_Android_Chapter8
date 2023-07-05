package com.example.my_app_eight.fragments.usage_fragments.item_setting_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
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
    private lateinit var binding: FragmentDescBinding

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
        descriptionUi(product)
        loadImage(product.photo)
    }

    private fun fetchProductDetails(productId: Int, token: String) {
        val apiService = RetrofitInstance.apiProduct

        apiService.getProductDetails(productId, token).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val product = response.body()
                    product?.let {
                        val imageUrl = it.photo
                        loadImage(imageUrl)
                        descriptionUi(it)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Could not catch product info",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun descriptionUi(product: ProductResponse) {
        binding.itemPrice.text = product.price
        binding.numbOfLike.text = product.like_count
        binding.nameOfItem.text = product.name
        binding.shortDesc.text = product.short_description
        binding.fullDesc.text = product.full_description

    }

    private fun loadImage(imageUrl: String) {
        Glide.with(requireContext())
            .load(imageUrl)
            .centerCrop()
            .into(binding.imgDesc)
    }
}