package com.gamefriends.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.gamefriends.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private val aunthenticationViewModel: AunthenticationViewModel by viewModels()

    companion object {
        const val EXTRA_ISFROMEDITPROFILE = "FALSE"
        const val EDIT_PROFILE_TRUE = "TRUE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authentication)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val isFromEditProfile = intent.getStringExtra(EXTRA_ISFROMEDITPROFILE)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            if (isFromEditProfile == EDIT_PROFILE_TRUE) {
                try {
                    navController.navigate(R.id.gamePlayedFragment)
                } catch (e: Exception) {
                    Log.e("AuthenticationActivity", "Error navigating to GamePlayedFragment: ${e.message}", e)
                }
            }
        }

    }
}