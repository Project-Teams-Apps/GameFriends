package com.gamefriends.ui.bio.gender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentGenderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenderFragment : Fragment(){

    private var _binding: FragmentGenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedGender: String

    private val genderViewModel: GenderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genderInput()

        binding.continueBtn.setOnClickListener {
            if (::selectedGender.isInitialized) {
                genderViewModel.genderBio(selectedGender).observe(viewLifecycleOwner) { data ->
                    when (data) {
                        is Resource.Error -> Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        is Resource.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        is Resource.Success -> it.findNavController().navigate(R.id.action_genderFragment_to_hobbyFragment)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please select a gender", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGenderBinding.inflate(inflater, container, false)
        return binding.root
    }



    private fun genderInput() {
        val spinner = binding.dropdownGenderMenu
        val genders = listOf("Male", "Female")

        spinner.setItems(genders)

        spinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            selectedGender = newItem
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}