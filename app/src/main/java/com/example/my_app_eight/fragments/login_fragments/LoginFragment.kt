package com.example.my_app_eight.fragments.login_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentLoginBinding
import com.example.my_app_eight.view_models.login_view_model.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkOccupancy()
        toRegNewUser()
    }

    private fun toRegNewUser() {
        binding.registrationTextBtnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_userRegFragment)
        }
    }

    private fun checkOccupancy() {
        binding.inputLogin.addTextChangedListener { text ->
            viewModel.onUsernameTextChanged(text)
        }
        binding.inputLoginPassword.addTextChangedListener { text ->
            viewModel.onPasswordTextChanged(text)
        }
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            val button = binding.btnLogin
            button.isEnabled = isEnabled
            button.setBackgroundResource(if (isEnabled) R.drawable.btn_active else R.drawable.btn_not_active)
        }
    }

    private fun callSnackBarAndNavigate() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val inflater = LayoutInflater.from(snackbar.context)
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar, null)

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

    private fun checkCorrectness() {
        viewModel.isUsernameValid.observe(viewLifecycleOwner) { isValid ->
            val inputLayout = binding.textInputFragmentLogin
            val editText = binding.inputLogin

            if (isValid) {
                inputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.inputBlack)
                editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.inputBlack))
            } else {
                inputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.red)
                editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
        viewModel.isPasswordValid.observe(viewLifecycleOwner) { isValid ->
            val inputLayout = binding.textInputFragmentPassword
            val editText = binding.inputLoginPassword

            if (isValid) {
                inputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.inputBlack)
                editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.inputBlack))
            } else {
                inputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.red)
                editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }
}