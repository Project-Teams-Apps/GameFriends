package com.gamefriends.ui.setting.reportbug

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.ActivityReportAbugBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportAbugActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportAbugBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReportAbugBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBackButton()
        setupSendBugReport()
    }

    private fun setupSendBugReport() {
        binding.continueBtn.setOnClickListener {
            val bugReport = binding.sendFeedBackEdt.text.toString()

            viewModel.sendBugReport(bugReport).observe(this) { apiResponse ->
                when(apiResponse) {
                    is Resource.Error -> {
                        binding.continueBtn.isEnabled = true
                        binding.progreesLoading.visibility = View.INVISIBLE
                    }
                    is Resource.Loading -> {
                        binding.continueBtn.isEnabled = false
                        binding.progreesLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.continueBtn.isEnabled = true
                        binding.progreesLoading.visibility = View.INVISIBLE
                        Toast.makeText(this, "Bug Report is success", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

}