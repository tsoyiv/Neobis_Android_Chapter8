package com.example.my_app_eight.fragments.usage_fragments.profile_fragments.user_menu_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentMyItemEmptyBinding

class MyItemEmptyFragment : Fragment() {

    private lateinit var binding : FragmentMyItemEmptyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyItemEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}