package com.example.my_app_eight.fragments.registration_fragments

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentCreatePasswordBinding
import com.example.my_app_eight.view_models.reg_view_model.CreatePasswordViewModel

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding
    val viewModel : CreatePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toRegUserPage()
        validatePassword()
    }

    private fun validatePassword() {

        binding.firstInputPassword.addTextChangedListener { text ->
            viewModel.onPasswordTextChanged(text)
        }
        binding.secondInputPassword.addTextChangedListener { text ->
            viewModel.onConfirmPasswordTextChanged(text)
        }
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.btnFinishReg.isEnabled = isEnabled
            binding.btnFinishReg.setBackgroundResource(if (isEnabled) R.drawable.btn_active else R.drawable.btn_not_active)
        }
    }

    private fun toRegUserPage() {
        binding.btnReturnCreateUserPage.setOnClickListener {
            findNavController().navigate(R.id.action_createPasswordFragment_to_userRegFragment)
        }
    }
}