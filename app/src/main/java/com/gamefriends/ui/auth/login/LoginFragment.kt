package com.gamefriends.ui.auth.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_startedThreeFragment)
        }

        binding.forgotPasswordBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_emailPasswordFragment)
        }

        loginAuth()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun loginAuth() {
        binding.signInBtn.setOnClickListener {
            val email = binding.emailLoginEdt.text.toString().trim()
            val password = binding.passwordLoginEdt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(password.length < 8) {
                binding.passwordLoginEdt.error = "Password Must 8 Character"
                binding.passwordEdtLayout.isPasswordVisibilityToggleEnabled = false
                return@setOnClickListener
            }

            loginViewModel.login(email, password).observe(viewLifecycleOwner) { data ->
                when (data) {
                    is Resource.Error -> {
                        binding.progreesLoading.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.progreesLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progreesLoading.visibility = View.GONE
                        data.data?.let { token ->
                            it.findNavController().navigate(R.id.action_loginFragment_to_main_activity)
                        } ?: run {
                            Toast.makeText(context, "Unexpected error: No token received", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


}