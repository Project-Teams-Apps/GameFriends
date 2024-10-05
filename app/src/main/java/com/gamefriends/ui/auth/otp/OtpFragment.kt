package com.gamefriends.ui.auth.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class OtpFragment : Fragment() {

    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private val data: OtpFragmentArgs by navArgs()
    private val otpViewModel: OtpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        otpAuth()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun otpAuth() {
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
            val otp = otpEditText.joinToString("") { it.text.toString() }

            if (email.isEmpty() || otp.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            otpViewModel.verifyOtpRegister(email, otp).observe(viewLifecycleOwner) { data ->
                when(data) {
                    is Resource.Error -> {
                        Toast.makeText(context, "OTP CODE IS NOT VERIFY", Toast.LENGTH_SHORT).show()
                        binding.progreesLoading.visibility = View.INVISIBLE
                    }
                    is Resource.Loading -> {
                        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                        binding.progreesLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                        it.findNavController().navigate(R.id.action_OtpFragment_to_gamePlayedFragment)
                    }
                }
            }
        }
    }

}