package com.example.my_app_eight.fragments.main_fragments.profile_fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileNumbBinding
import com.example.my_app_eight.util.PhoneNumberMaskWatcher
import com.example.my_app_eight.view_models.profile_view_models.NumbViewModel
import com.example.my_app_eight.view_models.profile_view_models.ProfileDataViewModel

class ProfileNumbFragment : Fragment() {

    private lateinit var binding: FragmentProfileNumbBinding
    val viewModel: NumbViewModel by viewModels()
    val viewModelHolder: ProfileDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileNumbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //logicForWritingNumber()
        val editText = view.findViewById<EditText>(R.id.edit_number)
        val phoneNumberMaskWatcher = PhoneNumberMaskWatcher(editText)
        editText.filters = arrayOf(InputFilter.LengthFilter(15))
        editText.addTextChangedListener(phoneNumberMaskWatcher)
        toGetCode()
        returnToUserInfo()
        saveNumber()
        checkOccupancy()
    }

    private fun checkOccupancy() {
        binding.editNumber.addTextChangedListener { text ->
            viewModel.onPasswordTextChanged(text)
        }
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            val button = binding.btnGetCodePage
            button.isEnabled = isEnabled
            button.setBackgroundResource(if (isEnabled) R.drawable.btn_active else R.drawable.btn_not_active)
        }
    }

    private fun saveNumber() {
        val numberEditText = binding.editNumber
        numberEditText.setText(viewModelHolder.number)
        numberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelHolder.number = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun returnToUserInfo() {
        binding.btnReturnEditPage.setOnClickListener {
            findNavController().navigate(R.id.action_profileNumbFragment_to_profileEditFragment2)
        }
    }

    private fun toGetCode() {
        binding.btnGetCodePage.setOnClickListener {
            findNavController().navigate(R.id.action_profileNumbFragment_to_profileCodeFragment)
        }
    }


    private fun logicForWritingNumber() {
        val editNumber = view?.findViewById<EditText>(R.id.edit_number)
        editNumber?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val phoneNumber = s.toString()
                viewModel.phoneNumber.set(phoneNumber)
            }
        })
    }
}