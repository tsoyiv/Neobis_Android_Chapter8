package com.example.my_app_eight.fragments.usage_fragments.add_item_fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentAddItemBinding
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.ImageConverter
import com.example.my_app_eight.util.ImageSettings
import com.example.my_app_eight.view_models.item_view_model.AddItemViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddItemFragment : Fragment() {
//
//    companion object {
//        const val IMAGE_REQUEST_CODE = 101
//        const val STORAGE_PERMISSION_CODE = 100
//    }

    private lateinit var binding: FragmentAddItemBinding
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var addButton: ImageView
    private lateinit var imageContainer: ViewGroup
    private var selectedImageFile: Uri? = null
    private val viewModel: AddItemViewModel by viewModels()
    private val imageList: MutableList<String> = mutableListOf()
    private var selectedImageUri: Uri? = null

    private val photos = Holder.imageList
//    private val imageInstr = mutableListOf<MultipartBody.Part>()

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
        //setupPhoto()
        callGallery()
        cancelBtn()
        saveItem()
        binding.btnReadyAddItem.setOnClickListener {
            createProduct()
        }
    }


//    private fun inputItem() {
//        val name = binding.editTextName.text.toString()
//        val price = binding.editTextPrice.text.toString()
//        val description = binding.editTextShortD.text.toString()
//        val photo = imageList.firstOrNull()
//
//        viewModel.inputItem(name, price, description, imageList)
//
//        viewModel.itemAddedSuccess.observe(viewLifecycleOwner) { isSuccess ->
//            if (isSuccess) {
//                Toast.makeText(requireContext(), "Product added", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Failed to add item", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            val imageUri = data.data
//            val imageView = ImageView(requireContext())
//            val layoutParams = LinearLayout.LayoutParams(
//                addButton.width,
//                addButton.height
//            )
//            layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
//            imageView.layoutParams = layoutParams
//            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//            imageView.setImageURI(imageUri)
//            imageView.clipToOutline = true
//            imageContainer.addView(imageView)
//
//            imageUri?.let {
//                val imagePath = getImagePath(imageUri)
//                imagePath?.let {
//                    imageList.clear()  // Clear the existing image list
//                    imageList.add(it)  // Add the selected image path
//                }
//            }
//        }
//    }


    private fun getImagePath(uri: Uri): String? {
        val implementOfImg = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, implementOfImg, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()

        val imagePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        return imagePath
    }

//    private fun uriToFile(uri: Uri): File {
//        val filePath = requireActivity().contentResolver.openInputStream(uri)?.use { inputStream ->
//            val file = File(requireContext().cacheDir, "temp_image")
//            FileOutputStream(file).use { outputStream ->
//                inputStream.copyTo(outputStream)
//            }
//            file.absolutePath
//        }
//        return File(filePath)
//    }

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
            createProduct()
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
                    imageUri = imageUri,
                    onSuccess = { findNavController().navigate(R.id.mainFragment) },
                    onError = { Toast.makeText(requireContext(), "Error creating product", Toast.LENGTH_SHORT).show() }
                )
            } else {
                Toast.makeText(requireContext(), "Error converting image", Toast.LENGTH_SHORT).show()
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
            data?.data?.let { uri ->
                selectedImageUri = uri
                // Display the selected image in an ImageView if needed
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICKER = 100
    }
}
//
//    private fun setupPhoto() {
//        addButton = binding.addImg
//        imageContainer = binding.imageContainer
//    }

//    private fun pickImageGallery() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Permission is not granted, request it
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                STORAGE_PERMISSION_CODE
//            )
//        } else {
//            // Permission is granted, launch the gallery picker
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, IMAGE_REQUEST_CODE)
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
//            val path: Uri? = data?.data
//            Log.d("profile", getRealPathFromURI(requireContext(), path!!))
//            val file = File(getRealPathFromURI(requireContext(), path))
//            val requestFile: RequestBody =
//                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//            val image = MultipartBody.Part.createFormData("image", file.name, requestFile)
//            imageInstr.add(image)
//            Log.d("Photos collection", imageInstr.toString())
//        }
//    }

//    private fun createProduct() {
//        val name = binding.editTextName.text.toString()
//        val price = binding.editTextPrice.text.toString()
//        val short_description = binding.editTextShortD.text.toString()
//        val full_description = binding.editTextLongD.text.toString()
//
//        val token = Holder.access_token
//        val authH = "Bearer $token"

//        photos.forEachIndexed { _, image ->
//            val file: File? = ImageConverter.getFile(requireContext(), image)
//            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
//            val imageInst = requestBody?.let {
//                MultipartBody.Part.createFormData("images", file.name, it)
//            }
//            imageInst?.let { imageInstr.add(it) }
//        }

//        for (image in photos) {
//            val path: Uri = image
//            val file = File(getRealPathFromURI(requireContext(), path))
//            val requestFile: RequestBody =
//                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//            imageInstr.add(MultipartBody.Part.createFormData("image", file.name, requestFile))
//        }

//        viewModel.inputItem(
//            requireContext(),
//            token = authH,
//            name = name.toRequestBody(),
//            price = price.toRequestBody(),
//            short_description = short_description.toRequestBody(),
//            full_description = full_description.toRequestBody(),
////            images = imageInstr,
//            onSuccess = { findNavController().navigate(R.id.mainFragment) },
//            onError = { Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show() }
//        )
//
//        Log.d(
//            "Check data",
//            "name = $name\n, price = $price\n, desc = $short_description"
//        )
//    }

//    private fun getRealPathFromURI(context: Context, uri: Uri): String {
//        var result: String? = null
//        val cursor = context.contentResolver.query(uri, null, null, null, null)
//        cursor?.let {
//            if (it.moveToFirst()) {
//                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                result = cursor.getString(columnIndex)
//            }
//            cursor.close()
//        }
//        return result ?: ""
//    }

        //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        ImageSettings.handleImageSelection(
//            this,
//            requestCode,
//            resultCode,
//            data,
//            addButton,
//            imageContainer
//        )
//    }
//        fun callSnackBarAndNavigate() {
//            val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
//            val inflater = LayoutInflater.from(snackbar.context)
//            val customSnackbarLayout = inflater.inflate(R.layout.custom_snackbar_add_item, null)
//
//            val snackbarView = snackbar.view
//            snackbarView.setBackgroundColor(Color.TRANSPARENT)
//
//            val layoutParams = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
//            layoutParams.gravity = Gravity.TOP
//            snackbarView.layoutParams = layoutParams
//
//            val snackbarLayout = FrameLayout(requireContext())
//            val frameLayoutParams = FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            snackbarLayout.layoutParams = frameLayoutParams
//
//            snackbarLayout.addView(customSnackbarLayout)
//            (snackbarView as Snackbar.SnackbarLayout).addView(snackbarLayout, 0)
//            snackbar.show()

//        view?.postDelayed({
//            findNavController().navigate(R.id.action_addItemFragment_to_mainFragment)
//        }, 500)
//        }

//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
//        startActivityForResult(intent, PICK_IMAGE_REQUEST)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            val imageUri = data.data
//            val imageView = ImageView(requireContext())
//            val layoutParams = LinearLayout.LayoutParams(
//                addButton.width,
//                addButton.height
//            )
//            layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
//            imageView.layoutParams = layoutParams
//            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//            imageView.setImageURI(imageUri)
//            imageView.setBackgroundResource(R.drawable.rounded_image_background)
//            imageView.clipToOutline = true // Add rounded corners
//            imageContainer.addView(imageView)
//
//            // Store the image URI in the selectedImageUri variable
//            selectedImageUri = imageUri
//        }
//    }