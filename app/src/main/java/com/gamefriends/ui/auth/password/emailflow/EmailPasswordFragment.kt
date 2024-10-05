package com.gamefriends.ui.auth.password.emailflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentEmailPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailPasswordFragment : Fragment() {

    private var _binding: FragmentEmailPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmailPassViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChangePassword()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEmailPasswordBinding.inflate(inflater)
        return binding.root
    }

    private fun setupChangePassword(){
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailRegisterEdt.text.toString()

            if (email.isEmpty()) {
                binding.emailRegisterEdt.error = "Field must be fill"
            }

            viewModel.changePassword(email).observe(viewLifecycleOwner) {data->
                when(data) {
                    is Resource.Error -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                        binding.emailRegisterEdt.error = "Email is Not Registered"
                    }
                    is Resource.Loading ->  {
                        binding.progreesLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                        val actions = EmailPasswordFragmentDirections.actionEmailPasswordFragmentToOtpForgotPasswordFragment(email)
                        it.findNavController().navigate(actions)
                    }
                }
            }
        }
    }

}