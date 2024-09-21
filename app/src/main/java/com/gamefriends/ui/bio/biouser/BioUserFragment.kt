package com.gamefriends.ui.bio.biouser

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
import com.gamefriends.databinding.FragmentBioUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BioUserFragment : Fragment() {

    private var _binding: FragmentBioUserBinding? = null
    private val binding get() = _binding!!

    private val bioViewModel: BioViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEditText()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBioUserBinding.inflate(inflater)
        return binding.root
    }

    private fun setupEditText(){
        binding.continueBtn.setOnClickListener {

            val bio = binding.bioEdt.text.toString()

            if (bio.length < 5 ) {
                Toast.makeText(requireContext(), "Bio At Least minimum 5 words", Toast.LENGTH_SHORT).show()
            } else if (bio.length > 100) {
                Toast.makeText(requireContext(), "Bio Maximum is 100 words", Toast.LENGTH_SHORT).show()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                bioViewModel.saveBioUser(bio)

                it.findNavController().navigate(R.id.action_bioFragment_to_uploadImageFragment)
            }
        }
    }

}