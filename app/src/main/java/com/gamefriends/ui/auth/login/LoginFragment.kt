package com.gamefriends.ui.auth.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        loginAuth()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun loginAuth() {
        binding.signInBtn.setOnClickListener {
            val email = binding.emailLoginEdt.text.toString().trim()
            val password = binding.passwordLoginEdt.text.toString().trim()

            // Input validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Observe the login response
            loginViewModel.login(email, password).observe(viewLifecycleOwner) { data ->
                when (data) {
                    is Resource.Error -> {
                        Toast.makeText(context, "Error: ${data.message}", Toast.LENGTH_SHORT).show()
                        Log.d("LoginFragment", "Login failed: ${data.message}")
                    }
                    is Resource.Loading -> {
                        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                        Log.d("LoginFragment", "Logging in...")
                    }
                    is Resource.Success -> {
                        data.data?.let { token ->
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            Log.d("LoginFragment", "loginAuth: ${token.token}")

                            // Navigate on success
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