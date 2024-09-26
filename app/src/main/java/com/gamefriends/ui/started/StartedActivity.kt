package com.gamefriends.ui.started

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gamefriends.R
import com.gamefriends.databinding.ActivityStartedBinding
import com.gamefriends.ui.auth.AuthenticationActivity
import com.gamefriends.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartedActivity : AppCompatActivity() {

    private var binding: ActivityStartedBinding? = null

    private val viewModel: StartedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding?.root?.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getToken.observe(this) { data ->
                val intent = if (data.token.isNotEmpty()) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, AuthenticationActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}