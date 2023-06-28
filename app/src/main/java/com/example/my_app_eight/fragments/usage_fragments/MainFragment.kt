package com.example.my_app_eight.fragments.usage_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentMainBinding
import com.example.my_app_eight.models.FavoriteRequest
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ItemAdapter
import com.example.my_app_eight.util.RecyclerListener
import com.example.my_app_eight.view_models.item_view_model.MainFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as HomeActivity).showBtm()
        super.onViewCreated(view, savedInstanceState)
        setupRV()


        itemAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("product", it)
            }
            findNavController().navigate(R.id.action_mainFragment_to_descFragment, bundle)
        }

        itemAdapter.setOnItemClickListener { product ->
            val action = MainFragmentDirections.actionMainFragmentToDescFragment(product)
            findNavController().navigate(action)
        }

        showProduct()
    }

    private fun showProduct() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                itemAdapter.setProducts(products)
            }
        }
        viewModel.fetchProducts()
    }

    private val listener = object : RecyclerListener {
        override fun deleteProduct(productId: Int) {
            TODO("Not yet implemented")
        }

//        override fun updateProduct(productId: Int, product: ProductResponse) {
//            TODO("Not yet implemented")
//        }

        override fun likeProduct(productId: Int) {
            logicToAddFave(productId)
        }

//        override fun unLike(productId: Int) {
//            //unlike(productId)
//        }
    }
    private fun unlike(item: Int) {
        val productAPI = RetrofitInstance.apiProduct
        val token = Holder.access_token
        val authHolder = "Bearer $token"
        val itemID = item

        productAPI.unlike(authHolder, itemID).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "removed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle error case
            }
        })
    }

    private fun logicToAddFave(productId: Int) {
        val token = Holder.access_token
        val authHolder = "Bearer $token"
        val favoriteRequest = FavoriteRequest(productId)
        val api = RetrofitInstance.apiProduct

        api.addToFavorites(authHolder, favoriteRequest).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                   Toast.makeText(requireContext(), "Product added to favorite products", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "You have already added product to favorite", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
            }
        })
    }

    private fun setupRV() {
        itemAdapter = ItemAdapter(listener, mutableListOf())
        val recyclerView = binding.recyclerview
        recyclerView.adapter = itemAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}