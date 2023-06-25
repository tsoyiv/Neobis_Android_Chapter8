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
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentLoginBinding
import com.example.my_app_eight.models.LoginRequest
import com.example.my_app_eight.models.LoginResponse
import com.example.my_app_eight.models.api.AuthAPI
import com.example.my_app_eight.models.api.RetrofitInstance
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.view_models.login_view_model.LoginViewModel
import com.example.my_app_eight.view_models.reg_view_model.HolderViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    val viewModel: LoginViewModel by viewModels()
    private val userAPI: AuthAPI = RetrofitInstance.apiAuth
    val hViewModel : HolderViewModel by activityViewModels()


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
            val request = LoginRequest(username, password)

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response: Response<LoginResponse> = withContext(Dispatchers.IO) {
                        userAPI.login(request)
                    }
                    if (response.isSuccessful) {

                        val loginResponse = response.body()
                        val accessToken = loginResponse?.access
                        val refreshToken = loginResponse?.refresh
                        if (refreshToken != null && accessToken != null) {
                            Holder.access_token = accessToken
                        }
                        val authHeader = "Bearer $accessToken"
                        Toast.makeText(requireContext(), authHeader, Toast.LENGTH_SHORT).show()
                        println(authHeader)
                        println(Holder.access_token)
                        hViewModel.username = username
                        //Toast.makeText(requireContext(), "You are IN", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    } else {
                        callSnackBarAndNavigate()
                    }
                } catch (t: Throwable) {
                    Toast.makeText(requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
                }
            }
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

    private fun checkCorrectness() {
        viewModel.isUsernameValid.observe(viewLifecycleOwner) { isValid ->
            val inputLayout = binding.textInputFragmentUsername
            val editText = binding.inputUsername

            if (isValid) {
                inputLayout.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.inputBlack)
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
                inputLayout.boxStrokeColor =
                    ContextCompat.getColor(requireContext(), R.color.inputBlack)
                editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.inputBlack))
            } else {
                inputLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.red)
                editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }
}