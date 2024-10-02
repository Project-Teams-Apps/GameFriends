package com.gamefriends.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.ActivitySettingBinding
import com.gamefriends.ui.auth.AuthenticationActivity
import com.gamefriends.ui.main.MainActivity
import com.gamefriends.ui.setting.account.AccountActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel : SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settingActivityMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBackButton()
        logout()
        accountBtn()
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logout(){
        binding.logoutBtn.setOnClickListener {
            showDialog(this)
        }
    }

    private fun accountBtn() {
        binding.accountBtn.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun editProfile() {
        binding.editProfileBtn.setOnClickListener {

        }
    }

    private fun showDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setTitle("Are you sure ?")
            .setPositiveButton("Logout") {_,_ ->
                viewModel.logoutUser().observe(this) { apiResponse ->
                    when(apiResponse) {
                        is Resource.Error -> Toast.makeText(this, apiResponse.message, Toast.LENGTH_SHORT).show()
                        is Resource.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                        is Resource.Success -> {
                            lifecycleScope.launch {
                                viewModel.logoutDatastore()
                            }
                            val intent = Intent(this, AuthenticationActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
            .setNegativeButton("Cancel") {dialog, _ ->
                dialog.dismiss()
            }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }



}