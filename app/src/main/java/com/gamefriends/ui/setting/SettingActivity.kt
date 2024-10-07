package com.gamefriends.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.gamefriends.ui.setting.account.AccountActivity
import com.gamefriends.ui.setting.feedback.SendFeedbackActivity
import com.gamefriends.ui.setting.reportbug.ReportAbugActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : AppCompatActivity(), View.OnClickListener {
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

        binding.reportBugBtn.setOnClickListener(this)
        binding.backButton.setOnClickListener(this)
        binding.logoutBtn.setOnClickListener(this)
        binding.accountBtn.setOnClickListener(this)
        binding.editProfileBtn.setOnClickListener(this)
        binding.sendFeedbackBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.reportBugBtn -> {
                val intent = Intent(this, ReportAbugActivity::class.java)
                startActivity(intent)
            }
            R.id.backButton -> {
                finish()
            }
            R.id.logoutBtn -> {
                showDialog(this)
            }
            R.id.accountBtn -> {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
            }
            R.id.editProfileBtn -> {
                val moveDataWithIntent = Intent(this, AuthenticationActivity::class.java)
                moveDataWithIntent.putExtra(AuthenticationActivity.EXTRA_ISFROMEDITPROFILE, "TRUE")
                startActivity(moveDataWithIntent)
            }
            R.id.sendFeedbackBtn -> {
                val intent = Intent(this, SendFeedbackActivity::class.java)
                startActivity(intent)
            }
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