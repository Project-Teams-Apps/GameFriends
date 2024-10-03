package com.gamefriends.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gamefriends.core.data.source.remote.response.Data
import com.gamefriends.core.data.source.remote.response.DataItem
import com.gamefriends.databinding.NotificationPlaceholderBinding

class NotificationAdapter: ListAdapter<DataItem,NotificationAdapter.ListViewHolder>(DIFF_CALLBACK){

    var onItemClick:((DataItem)-> Unit)? = null

    inner class ListViewHolder(private val binding: NotificationPlaceholderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(listUser: DataItem) {
            Glide.with(binding.root)
                .load(binding.idProfileUserImage)
                .into(binding.idProfileUserImage)
            binding.usernameTv.text = listUser.userAcceptId

            binding.acceptButton.setOnClickListener {
                onItemClick?.invoke(listUser)
                notifyItemChanged(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ListViewHolder {
        val binding = NotificationPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

