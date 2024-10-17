package com.gamefriends.ui.started

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("TAG", "Message: Notification is Granted")
        } else {
            Log.d("TAG", "Message: Notification is Not Granted")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartedBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

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