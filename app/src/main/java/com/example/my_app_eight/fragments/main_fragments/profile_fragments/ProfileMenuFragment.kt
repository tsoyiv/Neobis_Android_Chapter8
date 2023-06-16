package com.example.my_app_eight.fragments.main_fragments.profile_fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileMenuBinding
import kotlinx.android.synthetic.main.custom_dialog_logout.view.*

class ProfileMenuFragment : Fragment() {

    private lateinit var binding : FragmentProfileMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toEditPage()
        logOut()
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
            //TODO add confirm logic
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