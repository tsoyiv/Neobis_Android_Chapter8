package com.example.my_app_eight.fragments.usage_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentMainBinding
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ItemAdapter
import com.example.my_app_eight.view_models.item_view_model.MainFragmentViewModel
import com.google.android.material.snackbar.Snackbar
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

    private fun setupRV() {
        itemAdapter = ItemAdapter(mutableListOf())
        val recyclerView = binding.recyclerview
        recyclerView.adapter = itemAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun callSnackBarAndNavigate() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val inflater = LayoutInflater.from(snackbar.context)
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar_add_item, null)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = FrameLayout(requireContext())
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        snackbarLayout.layoutParams = layoutParams

        snackbarLayout.addView(customSnackbarLayout)
        (snackbarView as Snackbar.SnackbarLayout).addView(snackbarLayout, 0)
        snackbar.show()

//        view?.postDelayed({
//            findNavController().navigate(R.id.action_secondResetPasswordFragment_to_loginFragment)
//        }, 2000)
    }
}