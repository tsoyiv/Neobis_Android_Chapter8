package com.example.my_app_eight.fragments.usage_fragments.profile_fragments.user_menu_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentUserItemsBinding
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ItemAdapter
import com.example.my_app_eight.util.RecyclerListener
import com.example.my_app_eight.view_models.item_view_model.MainFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserItemsFragment : Fragment() {

    private lateinit var binding: FragmentUserItemsBinding
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnToUserMenu()
        setupRV()
        showProduct()
        //isImageEmptyVisibility()
    }


//    fun isImageEmptyVisibility() {
//        val recyclerV = binding.recyclerview
//        val imageEmpty = binding.imgEmptyUserItems
//        val textEmpty = binding.txtEmptyUserItems
//
//        itemAdapter.notifyDataSetChanged()
//
//        if (recyclerV.adapter?.itemCount == 0) {
//            imageEmpty.visibility = View.VISIBLE
//            textEmpty.visibility = View.VISIBLE
//        } else {
//            imageEmpty.visibility = View.GONE
//            textEmpty.visibility = View.GONE
//        }
//    }

    private fun showProduct() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                itemAdapter.setProducts(products)
            }
        }
        viewModel.fetchProducts()
    }

    private val listener = object : RecyclerListener {
        override fun deleteShoe(item: ProductResponse) {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.bottom_dialog, null)

            dialogView.delete_item.setOnClickListener {
                logicForAlertDialog(item)
            }
            dialogView.txtBtn_editItem.setOnClickListener {
                findNavController().navigate(R.id.action_userItemsFragment_to_addItemFragment)
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.show()
        }
    }

    private fun logicForAlertDialog(item: ProductResponse) {
        val productAPI = RetrofitInstance.apiProduct
        val token = Holder.access_token
        val authHolder = "Bearer $token"
        val itemID = item

        productAPI.deleteProduct(authHolder, itemID).enqueue(object : Callback<Void> {
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

    private fun setupRV() {
        itemAdapter = ItemAdapter(listener, mutableListOf())
        val recyclerView = binding.recyclerview
        recyclerView.adapter = itemAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun returnToUserMenu() {
        binding.btnReturnFromMyItemToMenuPage.setOnClickListener {
            findNavController().navigate(R.id.action_userItemsFragment_to_profileMenuFragment2)
        }
    }
}