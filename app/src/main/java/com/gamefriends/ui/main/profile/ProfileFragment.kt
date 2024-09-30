package com.gamefriends.ui.main.profile

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.marginStart
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.domain.model.ProfileUser
import com.gamefriends.databinding.FragmentProfileBinding
import com.google.android.material.chip.Chip
import com.qamar.curvedbottomnaviagtion.log
import com.qamar.curvedbottomnaviagtion.setMargins
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpProfile()
        logout()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    private fun setUpProfile() {
        viewModel.getProfileData.observe(viewLifecycleOwner) { datas ->
            if (datas.email.isEmpty()) {
                viewModel.getProfileUser().observe(viewLifecycleOwner) { data ->
                    when (data) {
                        is Resource.Error -> Log.d("Error", "Add Friend Request: Error")
                        is Resource.Loading -> Log.d("Loading", "Add Friend Request: Loading")
                        is Resource.Success -> data.data?.let { updateProfileUI(it) }
                    }
                }
            } else {
                updateProfileUI(datas)
            }
        }
    }

    private fun updateProfileUI(profile: ProfileUser) {
        binding.usernameTv.text = profile.name
        binding.countryTv.text = profile.location
        binding.genderTv.text = profile.gender
        Glide.with(binding.profileImage)
            .load(profile.profilePictureUrl.toUri())
            .into(binding.profileImage)
        binding.bioUserTv.text = profile.bio

        // Clear existing views in FlexBoxLayouts
        binding.gamePlayedFlexBox.removeAllViews()
        binding.hobbyFlexBox.removeAllViews()

        // Add gamePlayed chips
        profile.gamePlayed.forEach { game ->
            val chip = Chip(requireContext()).apply {
                text = game
                setPadding(8,8,8,8)
                setMargins(8,8,8,8)
                isEnabled = false
                setChipBackgroundColorResource(R.color.secondaryColor)
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            binding.gamePlayedFlexBox.addView(chip)
        }

        // Add hobby chips
        profile.hobby.forEach { hobby ->
            val chip = Chip(requireContext()).apply {
                text = hobby
                isEnabled = false
                setPadding(8,8,8,8)
                setMargins(8,8,8,8)
                setChipBackgroundColorResource(R.color.secondaryColor)
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            binding.hobbyFlexBox.addView(chip)
        }
    }

    private fun logout() {
        binding.logoutBtn.setOnClickListener {
            viewModel.logoutUser().observe(viewLifecycleOwner) { response ->
                when(response) {
                    is Resource.Error -> Log.d("Error", "Logout: Error")
                    is Resource.Loading -> Log.d("Loading", "Logout: Loading")
                    is Resource.Success -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.logoutDatastore()
                        }
                        it.findNavController().navigate(R.id.action_profileFragment_to_authActivity)
                    }
                }
            }

        }
    }
}