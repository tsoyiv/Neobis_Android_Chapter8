package com.example.my_app_eight.fragments.usage_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.databinding.FragmentMainBinding
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.ItemAdapter
import com.example.my_app_eight.util.RecyclerListener
import com.example.my_app_eight.view_models.item_view_model.MainFragmentViewModel

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
        itemAdapter = ItemAdapter(null, mutableListOf())
        val recyclerView = binding.recyclerview
        recyclerView.adapter = itemAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}