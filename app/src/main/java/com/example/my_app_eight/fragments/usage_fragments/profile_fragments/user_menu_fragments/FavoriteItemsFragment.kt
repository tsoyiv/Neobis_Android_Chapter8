package com.example.my_app_eight.fragments.usage_fragments.profile_fragments.user_menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentFavoriteItemsBinding
import com.example.my_app_eight.models.FavoriteResponse
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ItemAdapter
import com.example.my_app_eight.util.RecyclerListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteItemsFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteItemsBinding
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        returnToUserMenu()
        fetchProducts()
    }

    private fun fetchProducts() {
        val productAPI = RetrofitInstance.apiProduct
        val token = Holder.access_token
        val authHolder = "Bearer $token"

        productAPI.getFavorites(authHolder).enqueue(object : Callback<List<FavoriteResponse>> {
            override fun onResponse(
                call: Call<List<FavoriteResponse>>,
                response: Response<List<FavoriteResponse>>
            ) {
                if (response.isSuccessful) {
                } else {
                }
            }
            override fun onFailure(call: Call<List<FavoriteResponse>>, t: Throwable) {
               Toast.makeText(requireContext(), "Functionality will be added lately", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_favoriteItemsFragment_to_mainFragment)
            }
        })
    }

    private val listener = object : RecyclerListener {
        override fun deleteProduct(productId: Int) {
            TODO("Not yet implemented")
        }

//        override fun updateProduct(productId: Int, product: ProductResponse) {
//            TODO("Not yet implemented")
//        }

        override fun likeProduct(productId: Int) {
            TODO("Not yet implemented")
        }

//        override fun unLike(productId: Int) {
//            TODO("Not yet implemented")
//        }
    }

    private fun setupRV() {
        itemAdapter = ItemAdapter(listener, mutableListOf())
        val recyclerView = binding.recyclerview
        recyclerView.adapter = itemAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun returnToUserMenu() {
        binding.btnReturnMenuPage.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteItemsFragment_to_profileMenuFragment2)
        }
    }
}