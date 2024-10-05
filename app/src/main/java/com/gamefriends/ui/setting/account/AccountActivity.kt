package com.gamefriends.ui.setting.account

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.ActivityAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupBackBtn()

        setupAccountUi()
    }

    private fun setupBackBtn() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupAccountUi() {
        viewModel.getProfileUser().observe(this) {apiResponse ->
            when(apiResponse) {
                is Resource.Error -> Log.d("TAG", "setupAccountUi: ${apiResponse.message}")
                is Resource.Loading -> Log.d("TAG", "setupAccountUi: Loading")
                is Resource.Success ->{
                    Glide.with(binding.root)
                        .load(apiResponse.data?.profilePictureUrl)
                        .into(binding.editProfileimg)

                    binding.usernameTv.text = apiResponse.data?.name
                    binding.countryTv.text = apiResponse.data?.location
                    binding.genderTv.text = apiResponse.data?.gender
                }
            }
        }
    }
}