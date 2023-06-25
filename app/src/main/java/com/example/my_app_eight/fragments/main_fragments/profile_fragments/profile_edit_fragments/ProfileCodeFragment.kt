package com.example.my_app_eight.fragments.main_fragments.profile_fragments.profile_edit_fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileCodeBinding
import com.example.my_app_eight.models.VerifyCodeRequest
import com.example.my_app_eight.api.RetrofitInstance
import com.example.my_app_eight.view_models.login_view_model.LoginViewModel
import com.example.my_app_eight.view_models.profile_view_models.CodeViewModel

class ProfileCodeFragment : Fragment() {

    private lateinit var binding: FragmentProfileCodeBinding
    private lateinit var countDownTimer: CountDownTimer
    private val vm: CodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnToNumbPage()
        timerLogic()
        codeSend()
    }

    private fun codeCheck() {
        vm.isCodeVerified.observe(viewLifecycleOwner) { isVerified ->
            if (isVerified) {
                Toast.makeText(requireContext(), "Account activated", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_profileCodeFragment_to_profileEditFragment)
            } else {
                Toast.makeText(requireContext(), "Incorrect Code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun timerLogic() {
        binding.textViewInfo.text = "Повторный запрос"
        binding.textViewRetryRequest.visibility = View.GONE

        startTimer(60000)
    }

    private fun startTimer(duration: Long) {
        var isTimerRunning = false

        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60

                binding.timerTextView.text = String.format("%d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                isTimerRunning = false
                binding.textViewInfo.visibility = View.GONE
                binding.timerTextView.visibility = View.GONE
                binding.textViewRetryRequest.visibility = View.VISIBLE
            }
        }

        binding.textViewRetryRequest.setOnClickListener {
            if (!isTimerRunning) {
                startTimer(60000)
                binding.textViewRetryRequest.visibility = View.GONE
                binding.textViewInfo.visibility = View.VISIBLE
                binding.timerTextView.visibility = View.VISIBLE
            }
        }
        countDownTimer.start()
    }

    private fun returnToNumbPage() {
        binding.btnReturnAddNumb.setOnClickListener {
            findNavController().navigate(R.id.action_profileCodeFragment_to_profileNumbFragment)
        }
    }

    private fun codeSend() {
        val editText = binding.inputCode
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val input = s?.toString() ?: ""
                if (input.length == 4) {
                    codeCheck()
                }
            }
        })
    }
}