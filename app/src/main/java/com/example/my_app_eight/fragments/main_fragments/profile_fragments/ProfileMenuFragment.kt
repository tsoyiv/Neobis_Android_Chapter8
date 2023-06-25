package com.example.my_app_eight.fragments.main_fragments.profile_fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.HomeActivity
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileMenuBinding
import com.example.my_app_eight.view_models.reg_view_model.HolderViewModel
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*

class ProfileMenuFragment : Fragment() {

    private lateinit var binding : FragmentProfileMenuBinding
    val hViewModel : HolderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).showBtm()
        binding.userInitialName.text = hViewModel.username

        toEditPage()
        logOut()
        toLikedPage()
        toUserItem()
    }

    private fun toUserItem() {
        val clickListener = View.OnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment2_to_userItemsFragment)
        }
        binding.imgUserItems.setOnClickListener(clickListener)
        binding.btnTxtMyItems.setOnClickListener(clickListener)
        binding.btnArrowMyItems.setOnClickListener(clickListener)
    }

    private fun toLikedPage() {
        val clickListener = View.OnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment2_to_favoriteItemsFragment)
        }
        binding.imgLiked.setOnClickListener(clickListener)
        binding.btnTxtLikedItems.setOnClickListener(clickListener)
        binding.btnArrowLikedItems.setOnClickListener(clickListener)
    }

    private fun logOut() {
        val clickListener = View.OnClickListener {
            callDialog()
        }
        binding.btnImgLogOut.setOnClickListener(clickListener)
        binding.btnTxtLogOut.setOnClickListener(clickListener)
        binding.btnArrowLogOut.setOnClickListener(clickListener)
    }

    private fun callDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_logout, null)

        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogBinding.confirm_btn.setOnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment2_to_loginFragment)
            Toast.makeText(requireContext(), "You logged out from account", Toast.LENGTH_SHORT).show()
            myDialog.dismiss()
        }
        dialogBinding.text_cancel.setOnClickListener {
            myDialog.dismiss()
        }
    }

    private fun toEditPage() {
        binding.btnProfEdit.setOnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment2_to_profileEditFragment)
        }
    }
}