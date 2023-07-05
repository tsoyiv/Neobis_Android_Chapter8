package com.example.my_app_eight.util

import android.net.Uri

object Holder {
    var username = ""
    var email = ""
    var name = ""
    var surname = ""
    var birthday = ""
    var phoneNumber = ""
    var selectedImageUri: Uri? = null
    var access_token = ""

    val imageList: MutableList<Uri> = mutableListOf()

    fun addImageUri(uri: Uri) {
        imageList.add(uri)
    }
}