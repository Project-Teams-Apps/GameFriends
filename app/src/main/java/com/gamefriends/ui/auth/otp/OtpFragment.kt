package com.gamefriends.ui.auth.otp

import android.os.Bundle
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
        binding.sendOtpBtn.setOnClickListener {
            val email = data.email
            val otpFields = listOf(
                binding.otp1Edt.text.toString(),
                binding.otp2Edt.text.toString(),
                binding.otp3Edt.text.toString(),
                binding.otp4Edt.text.toString(),
                binding.otp5Edt.text.toString()
            )

            val otp = otpFields.joinToString("")

            if (email.isEmpty() || otp.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            otpViewModel.verifyOtpRegister(email, otp).observe(viewLifecycleOwner) { data ->
                when(data) {
                    is Resource.Error -> Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show()
                    is Resource.Loading -> Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                    is Resource.Success -> {
                        it.findNavController().navigate(R.id.action_OtpFragment_to_gamePlayedFragment)
                    }
                }
            }
        }
    }
}