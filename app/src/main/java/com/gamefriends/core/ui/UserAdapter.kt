package com.gamefriends.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gamefriends.R
import com.gamefriends.core.data.source.local.enitity.FeedUserEntity
import com.gamefriends.core.data.source.remote.response.ListItem
import com.gamefriends.core.domain.model.FeedUser
import com.gamefriends.databinding.FeedItemBinding
import com.qamar.curvedbottomnaviagtion.setMargins

class UserAdapter: PagingDataAdapter<FeedUserEntity ,UserAdapter.ListViewHolder>(DIFF_CALLBACK) {
    var onItemCLick:((FeedUserEntity) -> Unit)? = null

    inner class ListViewHolder(private val binding: FeedItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FeedUserEntity) {
            with(binding) {
                Glide.with(imageFeedPreview)
                    .load(data.profilePictureUrl)
                    .into(imageFeedPreview)
                usernameTv.text = data.name
                locationText.text = data.location
                genderTv.text = data.gender
                bioTv.text = data.bio

                flexboxGamePlayed.removeAllViews()
                hobbyGamePlayed.removeAllViews()

                data.gamePlayed.forEach { game->
                    val chip = TextView(itemView.context).apply {
                        text = game
                        setPadding(16, 8, 16, 8)
                        setMargins(3,0,3,0)
                        setBackgroundResource(R.drawable.chip_background)
                        setTextColor(ContextCompat.getColor(context, R.color.white))
                    }

                    flexboxGamePlayed.addView(chip)
                }

                data.hobby.forEach { hobby->
                    val chip = TextView(itemView.context).apply {
                        text = hobby
                        setPadding(16, 8, 16, 8)
                        setBackgroundResource(R.drawable.chip_background)
                        setTextColor(ContextCompat.getColor(context, R.color.white))
                    }

                    hobbyGamePlayed.addView(chip)
                }

                if (data.isPendingRequest) {
                    favAdd.isEnabled = false

                } else {
                    favAdd.isEnabled = true
                }
            }

        }

        init {
            binding.favAdd.setOnClickListener {
                val item = getItem(adapterPosition)
                if (item != null) {
                    onItemCLick?.invoke(item)
                    item.isPendingRequest = true
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedUserEntity>() {
            override fun areItemsTheSame(oldItem:FeedUserEntity, newItem: FeedUserEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FeedUserEntity, newItem: FeedUserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}