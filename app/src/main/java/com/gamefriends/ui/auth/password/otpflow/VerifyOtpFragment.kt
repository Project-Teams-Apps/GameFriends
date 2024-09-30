package com.gamefriends.ui.auth.password.otpflow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.gamefriends.databinding.FragmentVerifyOtpBinding
import com.gamefriends.ui.auth.otp.OtpFragmentArgs

class VerifyOtpFragment : Fragment() {

    private var _binding: FragmentVerifyOtpBinding? = null
    private val binding get() = _binding!!
    private val data: VerifyOtpFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOtp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVerifyOtpBinding.inflate(inflater)
        return binding.root
    }

    private fun setupOtp(){
        val otpEditText = listOf(
            binding.otp1Edt,
            binding.otp2Edt,
            binding.otp3Edt,
            binding.otp4Edt,
            binding.otp5Edt
        )

        for (i in otpEditText.indices) {
            otpEditText[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < otpEditText.size - 1) {
                        otpEditText[i + 1].requestFocus()
                    } else if (s?.length == 0 && i > 0) {
                        otpEditText[i - 1].requestFocus()
                    }
                }
            })
        }

        binding.sendOtpBtn.setOnClickListener {
            val email = data.email
            val otp = otpEditText.joinToString("") { data ->
                data.text.toString() }
            Log.d("TAG", "setupOtp: $email")
            Log.d("TAG", "setupOtp: $otp")
            val action = VerifyOtpFragmentDirections.actionOtpForgotPasswordFragmentToChangePasswordFragment(email, otp)
            it.findNavController().navigate(action)

        }

    }
}