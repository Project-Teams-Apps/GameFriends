package com.gamefriends.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gamefriends.core.data.source.remote.response.ChatHistory
import com.gamefriends.core.data.source.remote.response.FromUser
import com.gamefriends.core.data.source.remote.response.GetHistoryItem
import com.gamefriends.databinding.MessageItemBinding

class MessageAdapter: ListAdapter<GetHistoryItem, MessageAdapter.MessageViewHolder>(DIFF_CALLBACK) {

    inner class MessageViewHolder(private val binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetHistoryItem) {
            binding.usernameTv.text = data.fromUser?.name
            binding.lastMessageTv.text = data.fromUser?.messageText
            Glide.with(binding.idProfileUserImage)
                .load(data.fromUser?.profilePictureUrl)
                .into(binding.idProfileUserImage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageAdapter.MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetHistoryItem>() {
            override fun areItemsTheSame(oldItem: GetHistoryItem, newItem: GetHistoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem:GetHistoryItem, newItem: GetHistoryItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}