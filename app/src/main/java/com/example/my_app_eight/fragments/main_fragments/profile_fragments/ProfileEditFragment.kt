package com.example.my_app_eight.fragments.main_fragments.profile_fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileEditBinding
import com.example.my_app_eight.models.UserInfoRequest
import com.example.my_app_eight.models.api.RetrofitInstanceEdit
import com.example.my_app_eight.models.api.UserInfoAPI
import com.example.my_app_eight.view_models.profile_view_models.ProfileDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*

class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    val viewModelHolder: ProfileDataViewModel by activityViewModels()
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageView: ImageView
    private lateinit var userInfoAPI: UserInfoAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toAddNumber()
        callGallery()
        saveDataInFields()
        pickDate()
        returnToProfileInfo()
        saveEdit()
    }

    private fun saveEdit() {
        binding.btnReady.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser() {
        val accessToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg4MDMyOTg2LCJpYXQiOjE2ODc0MjgxODYsImp0aSI6ImQwNTE3NjdmMzdjMDRkYjk5YzJlZTRmM2I1YjZhNDkxIiwidXNlcl9pZCI6N30.aXUsvQ9VTiQWNwR54bbYm78Ln9C02DIPwEwkij5yDM8"

        val userInfo = UserInfoRequest(
            binding.editTextName.text.toString(),
            binding.editTextSurname.text.toString(),
            binding.editTextNickname.text.toString(),
            binding.editTextBirthday.text.toString()
        )
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response =
                    RetrofitInstanceEdit.api.updateUserInfo("Bearer $accessToken", userInfo)
                if (response.isSuccessful) {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_profileEditFragment_to_profileMenuFragment22)
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "The user did not be updated", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: HttpException) {
                // Handle HTTP exception
            } catch (e: Exception) {
                // Handle other exceptions
            }
        }
    }

//    private fun editProfile() {
//        userInfoAPI = RetrofitInstance.api
//        val userInfo = UserInfoRequest(
//            binding.editTextName.text.toString(),
//            binding.editTextSurname.text.toString(),
//            binding.editTextNickname.text.toString(),
//            binding.editTextBirthday.text.toString()
//        )
//
//        updateUserInfo(userInfo)
//    }
//
//    private fun updateUserInfo(userInfo: UserInfoRequest) {
//
//        val tokenManager = TokenManager(requireContext())
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg4MDMyOTg2LCJpYXQiOjE2ODc0MjgxODYsImp0aSI6ImQwNTE3NjdmMzdjMDRkYjk5YzJlZTRmM2I1YjZhNDkxIiwidXNlcl9pZCI6N30.aXUsvQ9VTiQWNwR54bbYm78Ln9C02DIPwEwkij5yDM8"
//
//        if (token != null) {
//            val call = userInfoAPI.updateUserInfo(userInfo)
//            call.enqueue(object : Callback<Unit> {
//                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(requireContext(), "updated", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Unit>, t: Throwable) {
//                    // Request failed, handle error here
//                }
//            })
//        } else {
//            Toast.makeText(requireContext(), "did not receive token", Toast.LENGTH_SHORT).show()
//        }

//        val call = userInfoAPI.updateUserInfo(token, userInfo)
//        call.enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(requireContext(), "updated", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                // Request failed, handle error here
//            }
//        })

    private fun callGallery() {
        val txt_img_req = binding.chooseImg
        txt_img_req.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        //val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        profileImageView = binding.profileImg

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            profileImageView.setImageURI(selectedImageUri)
        }
    }

    private fun returnToProfileInfo() {
        binding.btnReset.setOnClickListener {
            findNavController().navigate(R.id.action_profileEditFragment_to_profileMenuFragment2)
        }
    }

    private fun saveDataInFields() {
        val editTextName = binding.editTextName
        val editTextSurname = binding.editTextSurname
        val editTextNickname = binding.editTextNickname
        val editTextBirthday = binding.editTextBirthday

        editTextName.setText(viewModelHolder.name)
        editTextSurname.setText(viewModelHolder.surname)
        editTextNickname.setText(viewModelHolder.nickname)
        editTextBirthday.setText(viewModelHolder.birthday)

        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelHolder.name = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelHolder.surname = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelHolder.nickname = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextBirthday.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelHolder.birthday = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun toAddNumber() {
        binding.addNumber.setOnClickListener {
            findNavController().navigate(R.id.action_profileEditFragment_to_profileNumbFragment)
        }
    }

    private fun pickDate() {
        val datePicker = binding.editTextBirthday
        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
                    datePicker.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
}