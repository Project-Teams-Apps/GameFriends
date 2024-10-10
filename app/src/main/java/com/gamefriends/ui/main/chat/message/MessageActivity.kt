package com.gamefriends.ui.main.chat.message

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.ui.MessageAdapter
import com.gamefriends.databinding.ActivityMessageBinding
import com.gamefriends.ui.auth.otp.OtpFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding

    private val viewModel: MessageViewModel by viewModels()
    private val data: MessageActivityArgs by navArgs()

    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        setupLayoutUser()
        setupRecylerView()
        fetchHistoryMessage()
    }

    fun setupRecylerView() {
        adapter = MessageAdapter()

        binding.ChatRecylerView.apply {
            layoutManager = LinearLayoutManager(this@MessageActivity)
            adapter = this@MessageActivity.adapter
            addItemDecoration(DividerItemDecoration(this@MessageActivity, LinearLayoutManager.VERTICAL) )
        }
    }


    fun fetchHistoryMessage() {
        viewModel.fetchHistoryMessage(data.toUserId).observe(this) { apiResponse ->
            when(apiResponse) {
                is Resource.Error -> Log.d("TAG", "fetchHistoryMessage: ${apiResponse.message}")
                is Resource.Loading -> Log.d("TAG", "fetchHistoryMessage: Loading")
                is Resource.Success -> {
                    adapter.submitList(apiResponse.data?.data?.chatHistory?.getHistory)
                }
            }
        }
    }

    fun setupLayoutUser() {
        Glide.with(binding.idProfileUserImage)
            .load(data.profilePicture)
            .into(binding.idProfileUserImage)

        binding.notificationTv.text = data.name
    }
}