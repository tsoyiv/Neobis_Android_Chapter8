package com.example.my_app_eight.fragments.usage_fragments.add_item_fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentAddItemBinding
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ImageConverter
import com.example.my_app_eight.view_models.item_view_model.AddItemViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddItemFragment : Fragment() {

    companion object {
        const val REQUEST_IMAGE_PICKER = 100
    }

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var addButton: ImageView
    private lateinit var imageContainer: ViewGroup
    private val viewModel: AddItemViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()
        callGallery()
        cancelBtn()
        createProductFinish()
        checkCreating()
    }

    private fun createProductFinish() {
        binding.btnReadyAddItem.setOnClickListener {
            createProduct()
        }
    }

    private fun checkCreating() {
        viewModel.itemAddedSuccess.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT)
                    .show()
                callSnackBarAndNavigate()
                findNavController().navigate(R.id.mainFragment)
            } else {
                Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainFragment)
            }
        })
    }

    private fun createProduct() {
        val name = binding.editTextName.text.toString()
        val price = binding.editTextPrice.text.toString()
        val shortDescription = binding.editTextShortD.text.toString()
        val fullDescription = binding.editTextLongD.text.toString()

        val token = Holder.access_token
        val authHeader = "Bearer $token"

        selectedImageUri?.let { imageUri ->
            val imageFile = ImageConverter.getFile(requireContext(), imageUri)
            if (imageFile != null) {
                val imagePart = prepareImagePart(imageFile)
                val imageParts = listOf(imagePart)

                viewModel.inputItem(
                    requireContext(),
                    token = authHeader,
                    name = name,
                    price = price,
                    short_description = shortDescription,
                    full_description = fullDescription,
                    imageUri = imageUri
                )
            } else {
                Toast.makeText(requireContext(), "Error converting image", Toast.LENGTH_SHORT)
                    .show()
            }
        } ?: run {
            Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun prepareImagePart(imageFile: File): MultipartBody.Part {
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("photo", imageFile.name, requestFile)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {

            val imageView = ImageView(requireContext())

            data?.data?.let { uri ->
                selectedImageUri = uri
            }

            val layoutParams = LinearLayout.LayoutParams(
                addButton.width,
                addButton.height
            )
            layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
            imageView.layoutParams = layoutParams

            imageView.layoutParams = layoutParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageURI(selectedImageUri)
            imageView.setBackgroundResource(R.drawable.rounded_image_background)
            imageView.clipToOutline = true
            imageContainer.addView(imageView)
        }
    }

    private fun cancelBtn() {
        binding.btnCancelAddItem.setOnClickListener {
            callDialog()
        }
    }

    private fun callGallery() {
        addButton = binding.addImg
        imageContainer = binding.imageContainer
        addButton.setOnClickListener {
            openImagePicker()
        }
    }

    private fun callDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.custom_cancel_editing, null)

        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogBinding.confirm_btn.setOnClickListener {
            findNavController().navigate(R.id.action_addItemFragment_to_mainFragment)
            myDialog.dismiss()
        }
        dialogBinding.text_cancel.setOnClickListener {
            myDialog.dismiss()
        }
    }

    private fun callSnackBarAndNavigate() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val inflater = LayoutInflater.from(snackbar.context)
        val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar_add_item, null)

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
}