package com.example.my_app_eight.fragments.usage_fragments.profile_fragments.user_menu_fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentUserItemsBinding
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.*
import com.example.my_app_eight.view_models.item_view_model.MainFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_dialog.view.*
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*
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
    }

    private fun showProduct() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                itemAdapter.setProducts(products)

                val isEmpty = products.isEmpty()
                binding.imgEmptyUserItems.visibility = if (isEmpty) View.VISIBLE else View.GONE
                binding.txtEmptyUserItems.visibility = if (isEmpty) View.VISIBLE else View.GONE
            }
        }
        viewModel.fetchProducts()
    }


    private val listener = object : RecyclerListener {
        override fun deleteProduct(item: Int) {
        }

        override fun updateProduct(productId: Int, product: ProductResponse) {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.bottom_dialog, null)

            dialogView.delete_item.setOnClickListener {
                callDialog(productId)
                bottomSheetDialog.dismiss()
            }

            dialogView.txtBtn_editItem.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("productId", productId)
                val action =
                    UserItemsFragmentDirections.actionUserItemsFragmentToUpdateFragment(product)
                findNavController().navigate(action)
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.show()
        }

        override fun likeProduct(productId: Int) {
        }
    }

    fun deleteProduct(item: Int) {
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

    private fun callDialog(item: Int) {
        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_removeitem, null)

        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogBinding.confirm_btn.setOnClickListener {
            deleteProduct(item)
            callSnackBarAndNavigate()
            myDialog.dismiss()
        }
        dialogBinding.text_cancel.setOnClickListener {
            myDialog.dismiss()
        }
    }

    private fun callSnackBarAndNavigate() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val inflater = LayoutInflater.from(snackbar.context)
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar_removeitem, null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.TRANSPARENT)

        val layoutParams = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackbarView.layoutParams = layoutParams

        val snackbarLayout = FrameLayout(requireContext())
        val frameLayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        snackbarLayout.layoutParams = frameLayoutParams

        snackbarLayout.addView(customSnackbarLayout)
        (snackbarView as Snackbar.SnackbarLayout).addView(snackbarLayout, 0)
        snackbar.show()

        view?.postDelayed({
            findNavController().navigate(R.id.action_userItemsFragment_to_mainFragment)
        }, 500)
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