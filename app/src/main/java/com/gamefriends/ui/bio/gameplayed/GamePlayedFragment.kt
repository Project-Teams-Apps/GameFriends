package com.gamefriends.ui.bio.gameplayed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.databinding.FragmentGamePlayedBinding
import com.gamefriends.databinding.FragmentRegisterBinding


class GamePlayedFragment : Fragment() {
    private var _binding: FragmentGamePlayedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_gamePlayedFragment_to_genderFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGamePlayedBinding.inflate(inflater, container, false)
        return binding.root
    }

}