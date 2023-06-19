package com.example.my_app_eight.util

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberMaskWatcher(private val editText: EditText) : TextWatcher {

    private var isFormatting: Boolean = false
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }
        isFormatting = true

        val formattedText = formatPhoneNumber(s.toString())
        editText.setText(formattedText)
        editText.setSelection(formattedText.length)

        isFormatting = false
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        val digitsOnly = phoneNumber.replace("\\D+".toRegex(), "")
        val formattedNumber = StringBuilder()

        if (digitsOnly.length >= 1) {
            formattedNumber.append("0 (")
            if (digitsOnly.length >= 4) {
                formattedNumber.append(digitsOnly.substring(1, 4))
            } else {
                formattedNumber.append(digitsOnly.substring(1))
            }
            formattedNumber.append(") ")
        }

        if (digitsOnly.length >= 4) {
            if (digitsOnly.length >= 7) {
                formattedNumber.append(digitsOnly.substring(4, 7))
                formattedNumber.append(" ")
                if (digitsOnly.length >= 10) {
                    formattedNumber.append(digitsOnly.substring(7, 10))
                    if (digitsOnly.length > 10) {
                        formattedNumber.append(" ")
                        formattedNumber.append(digitsOnly.substring(10))
                    }
                } else {
                    formattedNumber.append(digitsOnly.substring(7))
                }
            } else {
                formattedNumber.append(digitsOnly.substring(4))
            }
        }
        return formattedNumber.toString()
    }
}
