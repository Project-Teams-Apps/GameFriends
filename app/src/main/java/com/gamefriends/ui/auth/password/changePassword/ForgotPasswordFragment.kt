package com.gamefriends.ui.auth.password.changePassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import com.gamefriends.ui.auth.password.changePassword.ForgotPasswordFragmentArgs


@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgotPasswordViewModel by viewModels()
    private val args: ForgotPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChangePassword()
    }

    private fun setupChangePassword() {
        binding.passwordEdt.addTextChangedListener(textWatcher)
        binding.verifyPasswordEdt.addTextChangedListener(textWatcher)

        binding.changePasswordBtn.setOnClickListener {
            val email = args.otp
            val otp = args.email
            val password = binding.passwordEdt.text.toString().trim()

            Log.d("TAG", "setupChangePassword: $email")

            viewModel.changePassword(email, otp, password).observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progreesLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                        showToast("Password changed successfully!")
                        findNavController().navigate(R.id.action_changePasswordFragment_to_loginFragment)
                    }
                    is Resource.Error -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                        showToast("Error")
                    }
                }
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateInputs()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun validateInputs() {
        val password = binding.passwordEdt.text.toString().trim()
        val verifyPassword = binding.verifyPasswordEdt.text.toString().trim()

        // Enable button if password is at least 8 characters and passwords match
        val isPasswordValid = password.length >= 8
        val doPasswordsMatch = password == verifyPassword

        binding.changePasswordBtn.isEnabled = isPasswordValid && doPasswordsMatch

        // Set error messages
        if (!isPasswordValid) {
            binding.passwordEdt.error = "Password must be at least 8 characters"
        } else {
            binding.passwordEdt.error = null
        }

        if (!doPasswordsMatch && verifyPassword.isNotEmpty()) {
            binding.verifyPasswordEdt.error = "Passwords do not match"
        } else {
            binding.verifyPasswordEdt.error = null
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
