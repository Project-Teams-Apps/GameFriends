package com.gamefriends.ui.started

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.databinding.FragmentStartedThreeBinding


class StartedThreeFragment : Fragment() {

    private var _binding : FragmentStartedThreeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_startedThreeFragment_to_loginFragment)
        }

        binding.signUpBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_startedThreeFragment_to_registerFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartedThreeBinding.inflate(inflater, container, false)
        return binding.root
    }


}