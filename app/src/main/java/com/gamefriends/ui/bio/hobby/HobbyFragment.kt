package com.gamefriends.ui.bio.hobby

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentHobbyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HobbyFragment : Fragment() {

    private var _binding: FragmentHobbyBinding? = null
    private val binding get() = _binding!!

    private val selectedHobby = mutableListOf<String>()

    private val viewModel: HobbyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        selectHobbyBio()
        setupClickListener()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHobbyBinding.inflate(inflater)
        return binding.root


    }


    private fun setupClickListener() {
        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.saveHobbyUser(selectedHobby)

                it.findNavController().navigate(R.id.action_hobbyFragment_to_locationFragment)
            }
        }
    }


    private fun selectHobbyBio() {
        val buttons = listOf(
            binding.footballBtn,
            binding.artBtn,
            binding.footballBtn,
            binding.cookBtn,
            binding.karaokeBtn,
            binding.musicBtn,
            binding.photoGraphyBtn,
            binding.shoppingBtn,
            binding.travelingtn,
            binding.runningBtn
        )

        buttons.forEach {button ->
            button.setOnClickListener {
                val gameName = button.text.toString()
                if (selectedHobby.contains(gameName)) {
                    selectedHobby.remove(gameName)
                    button.setBackgroundColor(resources.getColor(R.color.blackLow, null))
                } else {
                    if (selectedHobby.size < 5 ) {
                        selectedHobby.add(gameName)
                        Log.d("GamePlayed", "selectGamePlayedBio: $gameName")
                        button.setBackgroundColor(resources.getColor(R.color.secondaryColor, null))
                    } else {
                        Toast.makeText(context, "You Can Only select up To 5 Games", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}