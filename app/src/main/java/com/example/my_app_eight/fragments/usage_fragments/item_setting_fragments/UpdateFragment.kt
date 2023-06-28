package com.example.my_app_eight.fragments.usage_fragments.item_setting_fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.databinding.FragmentUpdateBinding
import com.example.my_app_eight.models.ProductPostRequest
import com.example.my_app_eight.models.ProductResponse
import com.example.my_app_eight.util.Holder
import com.example.my_app_eight.util.RecyclerListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog.view.*
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*
import kotlinx.android.synthetic.main.fragment_desc.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //cancelBtn()

//        val button: ImageView = view.findViewById(R.id.btn_ready_updateItem)
//        button.setOnClickListener {
//            // Perform the update operation
//            val itemId = arguments?.getInt("itemId") ?: -1
//            val productResponse = arguments?.getParcelable<ProductResponse>("productResponse")
//
//            if (itemId != -1 && productResponse != null) {
//                updateItem(itemId, productResponse)
//            }
//        }
    }

//    private fun receiveInfoItem() {
//        val previousPrice = previousProductResponse.price
//        val previousName = previousProductResponse.name
//        val previousShortDescription = previousProductResponse.description
//        val previousLongDescription = previousProductResponse.description
//
//        binding.updateTextPrice.ser.setText(previousPrice)
//        updateTextName.setText(previousName)
//        updateTextShortD.setText(previousShortDescription)
//        updateTextLongD.setText(previousLongDescription)
//    }
//
//    private val listener = object : RecyclerListener {
//        override fun deleteProduct(item: Int) {
//
//        }
//
////        override fun updateProduct(productId: Int, product: ProductResponse) {
////            val bottomSheetDialog = BottomSheetDialog(requireContext())
////            val inflater = layoutInflater
////            val dialogView = inflater.inflate(R.layout.bottom_dialog, null)
////
////            dialogView.btn_ready_updateItem.setOnClickListener {
////                updateItem(productId, product)
////            }
////            dialogView.txtBtn_editItem.setOnClickListener {
////                findNavController().navigate(R.id.action_userItemsFragment_to_updateFragment)
////                bottomSheetDialog.dismiss()
////            }
////            bottomSheetDialog.setContentView(dialogView)
////            bottomSheetDialog.show()
//
//        }
//
//        override fun likeProduct(productId: Int) {
//            TODO("Not yet implemented")
//        }
//
////        override fun unLike(productId: Int) {
////            TODO("Not yet implemented")
////        }
//    }
//
//    private fun updateItem(item : Int, productResponse: ProductResponse) {
//            val updatedProduct = ProductPostRequest(
//                updateText_price.text.toString(),
//                updateText_name.text.toString(),
//                updateText_shortD.text.toString(),
//                null
//            )
//
//            val api = RetrofitInstance.apiProduct
//            val token = Holder.access_token
//            val productId = item
//            val updateCall = api.updateProduct(token, productId, updatedProduct)
//
//            updateCall.enqueue(object : Callback<ProductResponse> {
//                override fun onResponse(
//                    call: Call<ProductResponse>,
//                    response: Response<ProductResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val updatedProductResponse = response.body()
//                        Toast.makeText(requireContext(), "updated", Toast.LENGTH_SHORT).show()
//                    } else {
//                        // Handle error response
//                    }
//                }
//
//                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                    // Handle failure
//                }
//            })
//    }
//
//    private fun cancelBtn() {
//        binding.btnCancelUpdateItem.setOnClickListener {
//            callDialog()
//        }
//    }
//    private fun callDialog() {
//        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_logout, null)
//
//        val myDialog = Dialog(requireContext())
//        myDialog.setContentView(dialogBinding)
//
//        myDialog.setCancelable(true)
//        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        myDialog.show()
//
//        dialogBinding.confirm_btn.setOnClickListener {
//            //TODO add navigation findNavController().navigate(R.id.)
//            myDialog.dismiss()
//        }
//        dialogBinding.text_cancel.setOnClickListener {
//            myDialog.dismiss()
//        }
//    }
}