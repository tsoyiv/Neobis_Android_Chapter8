package com.example.my_app_eight.fragments.usage_fragments.profile_fragments.user_menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentFavoriteItemsBinding
import com.example.my_app_eight.util.ItemAdapter

class FavoriteItemsFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteItemsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        returnToUserMenu()
    }
    private fun setupRV() {
        val adapter = ItemAdapter(mutableListOf())
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun returnToUserMenu() {
        binding.btnReturnMenuPage.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteItemsFragment_to_profileMenuFragment2)
        }
    }
}