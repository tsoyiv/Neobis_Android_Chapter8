package com.example.my_app_eight.fragments.registration_fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentCreatePasswordBinding
import com.example.my_app_eight.models.UserRegRequest
import com.example.my_app_eight.models.api.RetrofitInstance
import com.example.my_app_eight.view_models.reg_view_model.CreatePasswordViewModel
import com.example.my_app_eight.view_models.reg_view_model.HolderViewModel
import kotlinx.android.synthetic.main.fragment_create_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentCreatePasswordBinding
    val viewModel : CreatePasswordViewModel by viewModels()
    val viewModelHolder : HolderViewModel by activityViewModels()
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()
        toRegUserPage()
        creationPassword()
        checkPassword()
        passwordVisibility()
    }

    private fun passwordVisibility() {
        val firstInputPassword = binding.firstInputPassword
        val secondInputPassword = binding.secondInputPassword
        val passwordVisibilityButton = binding.passwordVisibilityButton
        passwordVisibilityButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }
    }
    private fun updatePasswordVisibility() {
        val passwordTransformation = if (isPasswordVisible) {
            null // Show the password as plain text
        } else {
            PasswordTransformationMethod.getInstance() // Show the password as dots
        }
        firstInputPassword.transformationMethod = passwordTransformation
        secondInputPassword.transformationMethod = passwordTransformation
    }

    private fun checkPassword() {
        val firstInputPassword = binding.firstInputPassword
        val secondInputPassword = binding.secondInputPassword

        firstInputPassword.addTextChangedListener(passwordTextWatcher)
        secondInputPassword.addTextChangedListener(passwordTextWatcher)
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val password1 = firstInputPassword.text.toString()
            val password2 = secondInputPassword.text.toString()

            if (password1 == password2) {
                validatePassword()
                firstInputPassword.setTextColor(Color.BLACK)
                secondInputPassword.setTextColor(Color.BLACK)
                passwordsMismatchText.visibility = View.GONE
            } else {
                firstInputPassword.setTextColor(Color.RED)
                secondInputPassword.setTextColor(Color.RED)
                passwordsMismatchText.visibility = View.VISIBLE
            }
        }
    }

    private fun creationPassword() {
        binding.btnFinishReg.setOnClickListener {
            val password1 = binding.firstInputPassword.text.toString()
            val password2 = binding.secondInputPassword.text.toString()

            if (password1 == password2) {
                viewModelHolder.password = password1
                registerUser()
            } else {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registerUser() {
        val api = RetrofitInstance.apiAuth
        val requestBody = UserRegRequest(
            username = viewModelHolder.username ?: "",
            email = viewModelHolder.email ?: "",
            password = viewModelHolder.password ?: "",
            confirm_password = viewModelHolder.password ?: ""
        )

        val call = api.registerUser(requestBody)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    //val email = viewModelHolder.email ?: ""
                    Toast.makeText(requireContext(), "User successfully registered.", Toast.LENGTH_SHORT).show()
                    //callDialog(email)
                    findNavController().navigate(R.id.action_createPasswordFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "User with this email already exist", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
            }
        })
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