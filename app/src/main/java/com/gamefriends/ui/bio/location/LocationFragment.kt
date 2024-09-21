package com.gamefriends.ui.bio.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val locationViewModel: LocationViewModel by viewModels()

    private lateinit var selectedLocation: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationInput()

        binding.continueBtn.setOnClickListener {
            if (::selectedLocation.isInitialized) {
                viewLifecycleOwner.lifecycleScope.launch {
                    locationViewModel.saveLocationUser(selectedLocation)

                    it.findNavController().navigate(R.id.action_locationFragment_to_bioFragment)
                }
            } else {
                Toast.makeText(requireContext(), "Please select a Location You Live Right now", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater)
        return binding.root
    }

    private fun locationInput() {
        val spinner = binding.dropdownLocationMenu
        val list = listOf(
            "Indonesia",
            "Malaysia",
            "Singapura",
            "Thailand",
            "Philippines",
            "Brunei Darussalam",
            "Vietnam",
            "Laos",
            "Myanmar",
            "Cambodia",
            "Timor Leste"
        )

        spinner.setItems(list)

        spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            selectedLocation = newItem
        }


    }

}