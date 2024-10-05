package com.gamefriends.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gamefriends.R
import com.gamefriends.core.data.source.remote.response.Data
import com.gamefriends.core.data.source.remote.response.DataItem
import com.gamefriends.core.data.source.remote.response.ListNotificationResponse
import com.gamefriends.databinding.NotificationPlaceholderBinding

class NotificationAdapter : ListAdapter<DataItem, NotificationAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((DataItem) -> Unit)? = null

    inner class ListViewHolder(private val binding: NotificationPlaceholderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listUser:DataItem) {
            // Load user profile image
            Glide.with(binding.root.context)
                .load(listUser.userRequest?.bioUser?.profilePicureUrl) // Ensure you have the correct URL here
                .placeholder(R.drawable.placeholder) // Add a placeholder
                .into(binding.idProfileUserImage)

            binding.usernameTv.text = listUser.userRequest?.name

            binding.acceptButton.setOnClickListener {
                onItemClick?.invoke(listUser) // Use let for null safety
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = NotificationPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id // Compare unique IDs for item comparison
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem // Use the default equals check
            }
        }
    }
}
