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
class SendFeedbackActivity : AppCompatActivity(), View.OnClickListener {
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

        binding.continueBtn.setOnClickListener(this)
        setupBackButton()
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.continueBtn -> {
                val feedback = binding.sendFeedBackEdt.text.toString()
                viewModel.sendFeedback(feedback).observe(this) {apiResponse ->
                    when(apiResponse) {
                        is Resource.Error -> {
                            Toast.makeText(this, "Feedback is Failed", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            binding.progreesLoading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progreesLoading.visibility = View.INVISIBLE
                            binding.sendFeedBackEdt.text.clear()
                            Toast.makeText(this, "Send Feedback Successfull", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}