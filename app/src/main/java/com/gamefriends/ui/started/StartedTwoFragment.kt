package com.gamefriends.ui.started

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.databinding.FragmentStartedTwoBinding


class StartedTwoFragment : Fragment() {

    private var _binding :FragmentStartedTwoBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getStartedBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_startedTwoFragment_to_startedThreeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartedTwoBinding.inflate(inflater, container, false)
        return binding.root
    }


}