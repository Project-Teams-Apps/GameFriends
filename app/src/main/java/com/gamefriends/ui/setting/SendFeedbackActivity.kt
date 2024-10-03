package com.gamefriends.ui.setting

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.gamefriends.R
import com.gamefriends.databinding.ActivitySendFeedbackBinding
import kotlinx.coroutines.launch

class SendFeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendFeedbackBinding

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

            val bio = binding.sendFeedBackEdt.text.toString()

            if (bio.length < 5 ) {
                Toast.makeText(this, "Bio At Least minimum 5 words", Toast.LENGTH_SHORT).show()
            } else if (bio.length > 100) {
                Toast.makeText(this, "Bio Maximum is 100 words", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("TAG", "sendFeedBackSetup: successfull")
            }
        }
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}