package com.gamefriends.ui.setting.feedback

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.databinding.ActivitySendFeedbackBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendFeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendFeedbackBinding

    private val viewModel: SendFeedbackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySendFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sendFeedBackSetup()
        setupBackButton()
    }

    private fun sendFeedBackSetup() {
        binding.continueBtn.setOnClickListener {

            val feedbackReport = binding.sendFeedBackEdt.text.toString()

            viewModel.sendFeedback(feedbackReport).observe(this) { apiReponse ->
                when(apiReponse) {
                    is Resource.Error -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                    }
                    is Resource.Loading -> {
                        binding.continueBtn.isEnabled = false
                        binding.progreesLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progreesLoading.visibility = View.INVISIBLE
                        Toast.makeText(this, "Send Feedback Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}