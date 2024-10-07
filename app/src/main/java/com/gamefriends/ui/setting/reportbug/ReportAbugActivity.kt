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
class ReportAbugActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityReportAbugBinding
    private val viewModel: ReportABugViewModel by viewModels()

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
        binding.continueBtn.setOnClickListener(this)
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.continueBtn -> {
                val bugReport = binding.bugReportEdt.text.toString().trim()
                viewModel.sendReportBug(bugReport).observe(this) {apiResponse ->
                    when(apiResponse) {
                        is Resource.Error -> {
                            binding.progreesLoading.visibility = View.INVISIBLE
                        }
                        is Resource.Loading -> {
                            binding.progreesLoading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progreesLoading.visibility = View.INVISIBLE
                            binding.bugReportEdt.text.clear()
                            Toast.makeText(this, "Sending Feedback Successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}