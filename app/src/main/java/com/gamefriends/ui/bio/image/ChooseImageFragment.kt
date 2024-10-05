package com.gamefriends.ui.bio.image

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.FragmentChooseImageBinding
import com.gamefriends.ui.utils.reduceFileImage
import com.gamefriends.ui.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseImageFragment : Fragment() {

    private var _binding: FragmentChooseImageBinding? = null
    private val binding get() = _binding!!

    private var selectedImage: Uri? = null

    private val viewModel: ChooseImageViewModel by viewModels()

    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
        if (uri != null) {
            selectedImage = uri
            showImage()
        } else {
            Log.d("TAG", "pickImageMedia: No Media Selected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pickImageButton.setOnClickListener {
            pickImageMedia()
        }

        showImage()
        setupClickListener()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChooseImageBinding.inflate(inflater)
        return binding.root
    }

    private fun showImage() {
        selectedImage.let {
            binding.previewImagesView.setImageURI(it)
        }
    }

    private fun pickImageMedia() {
        pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setupClickListener() {
        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getBioUser.observe(viewLifecycleOwner) { data ->
                    viewModel.editBioUser(data.bio, data.gender, data.gamePlayed, data.location, data.hobby).observe(viewLifecycleOwner) { data->
                        when(data) {
                            is Resource.Error -> Log.d("ErrorEditBioUser", "setupClickListener: Error")
                            is Resource.Loading -> Log.d("LoadingEditBioUser", "setupClickListener: Loading")
                            is Resource.Success -> {
                                Log.d("Edit Bio User", "setupClickListener: Upload Bio User Succes Fully")
                            }
                        }
                    }
                }

                val image = selectedImage?.let { uriToFile(it, requireContext()) }?.reduceFileImage()

                if (image != null) {
                    viewModel.uploadProfileImage(image).observe(viewLifecycleOwner) {data->
                        when(data) {
                            is Resource.Error -> {
                                binding.progreesLoading.visibility = View.INVISIBLE
                                Toast.makeText(requireContext(), "You Must upload a Photo", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Loading -> {
                                binding.progreesLoading.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                binding.progreesLoading.visibility = View.INVISIBLE
                                it.findNavController().navigate(R.id.main_activity)
                            }
                        }
                    }
                }

            }
        }
    }

}