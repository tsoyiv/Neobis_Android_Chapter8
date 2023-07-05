package com.example.my_app_eight.fragments.usage_fragments.item_setting_fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentUpdateBinding
import com.example.my_app_eight.fragments.usage_fragments.add_item_fragment.AddItemFragment
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ImageConverter
import com.example.my_app_eight.util.TokenInterceptor
import com.example.my_app_eight.view_models.item_view_model.UpdateViewModel
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {

    companion object {
        private const val REQUEST_IMAGE_PICKER = 100
    }

    private lateinit var binding: FragmentUpdateBinding
    val args: UpdateFragmentArgs by navArgs()
    private lateinit var product: ProductResponse
    private val viewModel: UpdateViewModel by viewModels()
    private var selectedImageUri: Uri? = null
    private lateinit var addButton: ImageView
    private lateinit var imageContainer: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPrevInfo()
        cancelBtn()
        updateItem()
        checkCreating()
        callGallery()
    }

    private fun cancelBtn() {
        binding.btnCancelUpdateItem.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_userItemsFragment)
        }
    }

    private fun updateItem() {
        binding.btnReadyUpdateItem.setOnClickListener {
            updateProduct()
        }
    }

    private fun fetchPrevInfo() {
        product = args.updateArgs
        setPrevInfoAboutProduct(product)
    }

    private fun checkCreating() {
        viewModel.itemAddedSuccess.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Toast.makeText(requireContext(), "Product updated successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.mainFragment)
            } else {
                Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.mainFragment)
            }
        })
    }

    private fun updateProduct() {
        val token = Holder.access_token
        val authHolder = "Bearer $token"
        updateItemLogic(product.id, authHolder)
    }

    private fun updateItemLogic(id: Int, token: String) {
        val name = binding.updateTextName.text.toString()
        val price = binding.updateTextPrice.text.toString()
        val shortDescription = binding.updateTextShortD.text.toString()
        val fullDescription = binding.updateTextLongD.text.toString()

        val token = Holder.access_token
        val authHeader = "Bearer $token"

        selectedImageUri?.let { imageUri ->
            val imageFile = ImageConverter.getFile(requireContext(), imageUri)
            if (imageFile != null) {

                viewModel.inputItem(
                    requireContext(),
                    token = authHeader,
                    id = id,
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

    private fun callGallery() {
        addButton = binding.updateImg
        imageContainer = binding.imageContainerUp
        addButton.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, AddItemFragment.REQUEST_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddItemFragment.REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {

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

    private fun setPrevInfoAboutProduct(product: ProductResponse) {
        binding.updateTextPrice.setText(product.price)
        binding.updateTextName.setText(product.name)
        binding.updateTextShortD.setText(product.short_description)
        binding.updateTextLongD.setText(product.full_description)
    }
}