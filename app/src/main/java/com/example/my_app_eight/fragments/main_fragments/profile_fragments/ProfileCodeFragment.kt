package com.example.my_app_eight.fragments.main_fragments.profile_fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.my_app_eight.R
import com.example.my_app_eight.databinding.FragmentProfileCodeBinding

class ProfileCodeFragment : Fragment() {

    private lateinit var binding : FragmentProfileCodeBinding
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //code()
        returnToNumbPage()
        timerLogic()
    }

    private fun timerLogic() {
        binding.textViewInfo.text = "Повторный запрос"
        binding.textViewRetryRequest.visibility = View.VISIBLE

        binding.textViewInfo.setOnClickListener {
            // TODO Handle click event here
        }

        binding.textViewRetryRequest.setOnClickListener {
            if (!isTimerRunning) {
                startTimer(5000)
                binding.textViewRetryRequest.visibility = View.GONE
                binding.textViewInfo.visibility = View.VISIBLE
                binding.timerTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun startTimer(duration: Long) {
        isTimerRunning = true

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

        countDownTimer.start()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()
    }

    private fun returnToNumbPage() {
        binding.btnReturnAddNumb.setOnClickListener {
            findNavController().navigate(R.id.action_profileCodeFragment_to_profileNumbFragment)
        }
    }

//    private fun code() {
//        val inputCode = binding.inputCode
//        inputCode.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                // Not used in this case
//            }
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                // Not used in this case
//            }
//            override fun afterTextChanged(s: Editable) {
//                val code = s.toString()
//                val formattedCodeBuilder = StringBuilder("0 0 0 0")
//                val formattedCode = MutableLiveData("0 0 0 0")
//
//                for (i in code.indices) {
//                    if (i < formattedCodeBuilder.length && Character.isDigit(code[i])) {
//                        formattedCodeBuilder.setCharAt(i * 2, code[i])
//                    }
//                }
//                formattedCode.value = formattedCodeBuilder.toString()
//            }
//        })
//    }
}