package com.gamefriends.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.gamefriends.R
import com.gamefriends.core.service.MyWorker
import com.gamefriends.databinding.ActivityMainBinding
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initNavHost()
        binding.setUpBottomNavigation()

    }

    private fun initNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun ActivityMainBinding.setUpBottomNavigation() {
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(HOME_ITEM, "Home", R.drawable.home_icon),
            CurvedBottomNavigation.Model(LISTCHAT_ITEM, "Chats", R.drawable.chats_icon),
            CurvedBottomNavigation.Model(NOTIF_ITEM, "Notif", R.drawable.notif_icon),
            CurvedBottomNavigation.Model(PROFILE_ITEM, "Profile", R.drawable.profile_icon)
        )

        bottomNavigation.apply {
            bottomNavigationItems.forEach{add(it)}
            setOnClickMenuListener {
                navController.navigate(it.id)
            }
            show(HOME_ITEM)
            setupNavController(navController)
        }
    }


    companion object {
        val HOME_ITEM = R.id.homeFragment
        val LISTCHAT_ITEM = R.id.listChatFragment
        val NOTIF_ITEM = R.id.notificationFragment
        val PROFILE_ITEM = R.id.profileFragment
    }
}