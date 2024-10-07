package com.gamefriends.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gamefriends.core.data.source.remote.response.GetListMessageItem
import com.gamefriends.databinding.ListChatItemBinding

class ListChatsAdapter: ListAdapter<GetListMessageItem, ListChatsAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val binding: ListChatItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetListMessageItem) {
            binding.usernameTv.text = data.receiverName
            binding.lastMessageTv.text = data.lastMessage
            Glide.with(binding.idProfileUserImage)
                .load(data.receiverProfilePictureUrl)
                .into(binding.idProfileUserImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ListViewHolder {
        val binding = ListChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetListMessageItem>() {
            override fun areItemsTheSame(
                oldItem: GetListMessageItem,
                newItem: GetListMessageItem
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: GetListMessageItem,
                newItem: GetListMessageItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}