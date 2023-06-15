package com.example.my_app_eight.view_models.profile_view_models

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class NumbViewModel : ViewModel() {
    val phoneNumber = ObservableField<String>()
}