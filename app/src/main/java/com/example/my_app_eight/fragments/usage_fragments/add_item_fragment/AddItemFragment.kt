package com.example.my_app_eight.fragments.usage_fragments.add_item_fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentAddItemBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*

class AddItemFragment : Fragment() {

    private lateinit var binding : FragmentAddItemBinding
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var addButton: ImageView
    private lateinit var imageContainer: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()

        callGallery()
        cancelBtn()
        saveItem()
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

    private fun saveItem() {
        binding.btnReadyAddItem.setOnClickListener {
            callSnackBarAndNavigate()
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
            openGallery()
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

        view?.postDelayed({
            findNavController().navigate(R.id.action_addItemFragment_to_mainFragment2)
        }, 500)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val imageView = ImageView(requireContext())
            val layoutParams = LinearLayout.LayoutParams(
                addButton.width,
                addButton.height
            )
            layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
            imageView.layoutParams = layoutParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageURI(imageUri)
            imageView.setBackgroundResource(R.drawable.rounded_image_background)
            imageView.clipToOutline = true // Add rounded corners
            imageContainer.addView(imageView)
        }
    }
}