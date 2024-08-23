package com.gamefriends

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gamefriends.databinding.ActivityMainBinding
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

//    private fun ActivityMainBinding.setUpBottomNavigation() {
//        val bottomNavigationItems = mutableListOf(
//            CurvedBottomNavigation.Model()
//        )
//    }


    companion object {
        val HOME_ITEM = R.id.homeFragment
        val LISTCHAT_ITEM = R.id.listChatFragment
        val NOTIF_ITEM = R.id.notificationFragment
        val PROFILE_ITEM = R.id.profileFragment
    }
}