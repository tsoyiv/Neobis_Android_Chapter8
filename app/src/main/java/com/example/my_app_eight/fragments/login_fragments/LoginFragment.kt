package com.example.my_app_eight.fragments.login_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.ScreenActivity
import com.example.my_app_eight.databinding.FragmentLoginBinding
import com.example.my_app_eight.models.LoginRequest
import com.example.my_app_eight.models.LoginResponse
import com.example.my_app_eight.models.api.RetrofitInstance
import com.example.my_app_eight.view_models.login_view_model.LoginViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        (requireActivity() as HomeActivity).hide()

        checkOccupancy()
        toRegNewUser()
        loginCheck()
        //testToProfilePage()
    }

    private fun loginCheck() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { loginResponse ->
            if (loginResponse != null) {
                Toast.makeText(requireContext(), "You are IN", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_profileMenuFragment2)
            } else {
                checkCorrectness()
                callSnackBarAndNavigate()
                Toast.makeText(requireContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputLoginPassword.text.toString()

            viewModel.login(username, password)
        }
    }

    private fun testToProfilePage() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    private fun toRegNewUser() {
        binding.registrationTextBtnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_userRegFragment)
        }
    }

    private fun checkOccupancy() {
        binding.inputUsername.addTextChangedListener { text ->
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
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar_error, null)

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
            val inputLayout = binding.textInputFragmentUsername
            val editText = binding.inputUsername

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