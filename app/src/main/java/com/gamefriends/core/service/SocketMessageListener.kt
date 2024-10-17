package com.gamefriends.core.service

interface SocketMessageListener {
    fun onMessageReceived(fromUser: String, textMessage: String)
}