package com.example.my_app_eight.fragments.login_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.api.AuthAPI
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentLoginBinding
import com.example.my_app_eight.view_models.login_view_model.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val vm: LoginViewModel by viewModels()
    private val authAPI: AuthAPI = RetrofitInstance.apiAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()

        checkOccupancy()
        toRegNewUser()
        login()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputLoginPassword.text.toString()
            vm.login(username, password)
        }

        vm.loginSuccess.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Toast.makeText(requireContext(), "You are IN", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            } else {
                callSnackBarAndNavigate()
            }
        })
        vm.loginError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
        })
    }

    private fun toRegNewUser() {
        binding.registrationTextBtnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_userRegFragment)
        }
    }

    private fun checkOccupancy() {
        binding.inputUsername.addTextChangedListener { text ->
            vm.onUsernameTextChanged(text)
        }
        binding.inputLoginPassword.addTextChangedListener { text ->
            vm.onPasswordTextChanged(text)
        }
        vm.isButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            val button = binding.btnLogin
            button.isEnabled = isEnabled
            button.setBackgroundResource(if (isEnabled) R.drawable.btn_active else R.drawable.btn_not_active)
        }
    }

    private fun callSnackBarAndNavigate() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val inflater = LayoutInflater.from(snackbar.context)
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar_error, null)

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
    }
}