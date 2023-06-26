package com.example.my_app_eight.fragments.main_fragments.profile_fragments.profile_edit_fragments

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
import com.example.my_app_eight.models.UserDataRequest
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.view_models.profile_view_models.ProfileDataViewModel
import com.example.my_app_eight.view_models.reg_view_model.HolderViewModel
import java.util.*

class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    private val vm: ProfileDataViewModel by activityViewModels()
    private val vmH : HolderViewModel by activityViewModels()
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageView: ImageView

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
        binding.changeEmail.text = vmH.email

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
        val accessToken = Holder.access_token

        val userInfo = UserDataRequest(
            binding.editTextName.text.toString(),
            binding.editTextSurname.text.toString(),
            binding.editTextNickname.text.toString(),
            binding.editTextBirthday.text.toString()
        )
        vm.updateUser(accessToken, userInfo)

        vm.updateResult.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_profileEditFragment_to_profileMenuFragment22)
            } else {
                Toast.makeText(requireContext(), "The user was not updated", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

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
        val editTextHoldUsername = binding.editTextNickname

        editTextName.setText(vm.name)
        editTextSurname.setText(vm.surname)
        editTextNickname.setText(vm.nickname)
        editTextBirthday.setText(vm.birthday)
        editTextHoldUsername.setText(vmH.username)

        editTextHoldUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vmH.username = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.name = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.surname = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.nickname = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editTextBirthday.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.birthday = s?.toString() ?: ""
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