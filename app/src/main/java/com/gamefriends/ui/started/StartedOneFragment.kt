package com.gamefriends.ui.started

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.databinding.FragmentStartedOneBinding

class StartedOneFragment : Fragment() {

    private var _binding: FragmentStartedOneBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextBtn.setOnClickListener {view->
            view.findNavController().navigate(R.id.action_startedOneFragment_to_startedTwoFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartedOneBinding.inflate(layoutInflater,container ,false)
        return binding.root
    }


}