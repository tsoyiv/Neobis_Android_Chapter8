package com.example.my_app_eight.fragments.main_fragments.add_item_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentAddItemBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddItemFragment : Fragment() {

    private lateinit var binding : FragmentAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}