package com.gamefriends.ui.bio.gameplayed

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
import com.gamefriends.databinding.FragmentGamePlayedBinding
import com.gamefriends.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GamePlayedFragment : Fragment() {
    private var _binding: FragmentGamePlayedBinding? = null
    private val binding get() = _binding!!

    private val gamePlayedViewModel: GamePlayedViewModel by viewModels()

    private val selectedGames = mutableListOf<String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectGamePlayedBio()
        setupClickListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGamePlayedBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupClickListener() {
        binding.continueBtn.setOnClickListener {
            gamePlayedViewModel.gamePlayedBio(selectedGames).observe(viewLifecycleOwner) { data->
                when(data) {
                    is Resource.Error -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    is Resource.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    is Resource.Success -> {
                        it.findNavController().navigate(R.id.action_gamePlayedFragment_to_genderFragment)
                    }
                }
            }
        }
    }

    private fun selectGamePlayedBio(){
        val buttons = listOf(
            binding.hokBtn,
            binding.lolBtn,
            binding.apexBtn,
            binding.codmBtn,
            binding.csgoBtn,
            binding.chessBtn,
            binding.dota2Btn,
            binding.pubgmBtn,
            binding.fortniteBtn,
            binding.valorantBtn,
            binding.genshinImpactBtn,
            binding.mobileLegendBtn,
        )

        buttons.forEach {button ->
            button.setOnClickListener {
                val gameName = button.text.toString()
                if (selectedGames.contains(gameName)) {
                    selectedGames.remove(gameName)
                    button.setBackgroundColor(resources.getColor(R.color.blackLow, null))
                } else {
                    if (selectedGames.size < 5 ) {
                        selectedGames.add(gameName)
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