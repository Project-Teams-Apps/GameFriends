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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authentication)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            aunthenticationViewModel.getToken.observe(this) { user ->
                if (user.token.isNotEmpty()) {
                    Log.d("AuthActivity", "Token is not empty: ${user.token}")
                    try {
                        val mainNavController = findNavController(R.id.container)
                        if (mainNavController.currentDestination?.id != R.id.homeFragment) {
                            mainNavController.navigate(R.id.main_activity)
                        }
                    } catch (e: Exception) {
                        Log.e("AuthActivity", "Navigation error: ${e.message}")
                    }
                } else {
                    Log.d("AuthActivity", "Token is empty.")
                }
            }
        }
    }
}