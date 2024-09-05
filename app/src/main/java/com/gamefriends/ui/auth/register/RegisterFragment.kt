package com.gamefriends.ui.auth.register

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
import com.gamefriends.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel : RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_startedThreeFragment)
        }

        registerAuth()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun registerAuth() {
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailRegisterEdt.text.toString()
            val name = binding.nameRegisterEdt.text.toString()
            val password = binding.passwordRegisterEdt.text.toString()

            if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(email, name, password).observe(viewLifecycleOwner) { data->
                when (data) {
                    is Resource.Error -> {
                        Toast.makeText(context, "Error: ${data.message}", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        val action = RegisterFragmentDirections.actionRegisterFragmentToOtpFragment(email)
                        it.findNavController().navigate(action)
                    }
                }

            }
        }
    }
}