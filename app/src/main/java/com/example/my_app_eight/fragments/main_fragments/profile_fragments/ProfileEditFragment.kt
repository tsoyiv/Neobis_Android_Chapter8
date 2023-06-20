package com.example.my_app_eight.fragments.main_fragments.profile_fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileEditBinding
import com.example.my_app_eight.view_models.profile_view_models.ProfileDataViewModel
import java.util.*

class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    val viewModelHolder: ProfileDataViewModel by activityViewModels()

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
        saveDataInFields()
        pickDate()
        returnToProfileInfo()
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