package com.gamefriends.ui.main.chat.message

import SocketIOClient
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
import com.gamefriends.BuildConfig
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.data.source.remote.response.FromUser
import com.gamefriends.core.data.source.remote.response.GetHistoryItem
import com.gamefriends.core.ui.MessageAdapter
import com.gamefriends.databinding.ActivityMessageBinding
import com.gamefriends.ui.auth.otp.OtpFragmentArgs
import com.gamefriends.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding
    private val viewModel: MessageViewModel by viewModels()
    private val data: MessageActivityArgs by navArgs()
    private lateinit var adapter: MessageAdapter
    private val messages: MutableList<GetHistoryItem> = mutableListOf()
    private lateinit var socketManager: SocketIOClient
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        setupLayoutUser()
        setupRecylerView()
        fetchHistoryMessage()
        setupSocketIo()
    }

    fun setupSocketIo() {
        viewModel.getToken.observe(this) { dataToken ->
            socketManager = SocketIOClient(this@MessageActivity, BuildConfig.BASE_URL, dataToken.token, dataToken.userId)

            // Listen for incoming messages
            socketManager.connect { newMessage ->
                // Add the new message to the messages list
                messages.add(newMessage)
                adapter.submitList(messages.toList()) // Update the adapter with the full list
                binding.ChatRecylerView.scrollToPosition(messages.size - 1) // Scroll to the latest message
                notificationHelper.sendNotification(newMessage.fromUser?.name ?: "New Message", newMessage.fromUser?.messageText ?: "")
            }
        }

        binding.sendBtn.setOnClickListener {
            val textMessage = binding.messageEdt.text.toString()
            val toUserId = data.toUserId

            // Create a new message object for the sent message
            val sentMessage = GetHistoryItem(
                fromUser = FromUser(
                    name = data.name, // Change to the sender's name if needed
                    profilePictureUrl = data.profilePicture,
                    messageText = textMessage
                )
            )

            // Add the sent message to the messages list
            messages.add(sentMessage)
            adapter.submitList(messages.toList()) // Update the adapter with the full list
            binding.ChatRecylerView.scrollToPosition(messages.size - 1) // Scroll to the latest message

            // Send the message through Socket.IO
            socketManager.sendMessage(textMessage, toUserId)
            binding.messageEdt.text.clear() // Clear the EditText after sending
        }
    }

    fun setupRecylerView() {
        adapter = MessageAdapter()
        binding.ChatRecylerView.apply {
            layoutManager = LinearLayoutManager(this@MessageActivity)
            adapter = this@MessageActivity.adapter
            addItemDecoration(DividerItemDecoration(this@MessageActivity, LinearLayoutManager.VERTICAL))
        }
    }

    fun fetchHistoryMessage() {
        viewModel.fetchHistoryMessage(data.toUserId).observe(this) { apiResponse ->
            when (apiResponse) {
                is Resource.Error -> Log.d("TAG", "fetchHistoryMessage: ${apiResponse.message}")
                is Resource.Loading -> Log.d("TAG", "fetchHistoryMessage: Loading")
                is Resource.Success -> {
                    // Clear current messages and add fetched messages
                    messages.clear() // Clear existing messages if needed
                    apiResponse.data?.data?.chatHistory?.getHistory?.let { history ->
                        // Filter out null values before adding to messages
                        messages.addAll(history.filterNotNull()) // Add all non-null fetched messages
                    }
                    adapter.submitList(messages.toList()) // Update the adapter with the full list
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
