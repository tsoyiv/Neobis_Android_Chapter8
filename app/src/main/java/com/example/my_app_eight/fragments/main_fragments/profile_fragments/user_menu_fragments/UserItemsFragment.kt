package com.example.my_app_eight.fragments.main_fragments.profile_fragments.user_menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentUserItemsBinding
import com.example.my_app_eight.databinding.FragmentUserRegBinding

class UserItemsFragment : Fragment() {

    private lateinit var binding : FragmentUserItemsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnToUserMenu()
    }

    private fun returnToUserMenu() {
        binding.btnReturnFromMyItemToMenuPage.setOnClickListener {
            findNavController().navigate(R.id.action_userItemsFragment_to_profileMenuFragment2)
        }
    }
}