package com.example.my_app_eight.fragments.registration_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentUserRegBinding
import com.example.my_app_eight.view_models.reg_view_model.HolderViewModel
import com.example.my_app_eight.view_models.reg_view_model.UserRegViewModel

class UserRegFragment : Fragment() {

    private lateinit var binding: FragmentUserRegBinding
    val viewModel: UserRegViewModel by viewModels()
    val viewModelHolder : HolderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnToLoginPage()
        toCreatePassword()
        checkOccupancy()
        fillUserInfo()
    }

    private fun fillUserInfo() {
        binding.btnRegToPasswordPage.setOnClickListener {
            viewModelHolder.username = binding.regInputUsername.text.toString()
            viewModelHolder.email = binding.regInputEmail.text.toString()

            findNavController().navigate(R.id.action_userRegFragment_to_createPasswordFragment)
        }
    }

    private fun toCreatePassword() {
        binding.btnRegToPasswordPage.setOnClickListener {
            findNavController().navigate(R.id.action_userRegFragment_to_createPasswordFragment)
        }
    }

    private fun returnToLoginPage() {
        binding.btnReturnToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_userRegFragment_to_loginFragment)
        }
    }

    private fun checkOccupancy() {
        binding.regInputUsername.addTextChangedListener { text ->
            viewModel.onUsernameTextChanged(text)
        }
        binding.regInputEmail.addTextChangedListener { text ->
            viewModel.onEmailTextChanged(text)
        }
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            val button = binding.btnRegToPasswordPage
            button.isEnabled = isEnabled
            button.setBackgroundResource(if (isEnabled) R.drawable.btn_active else R.drawable.btn_not_active)
        }
    }
}