package com.dicoding.moodmate.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.databinding.ItemChatLeftBinding
import com.dicoding.moodmate.databinding.ItemChatRightBinding

class ChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(ChatDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_LEFT = 1
        private const val VIEW_TYPE_RIGHT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RIGHT) {
            val binding = ItemChatRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RightChatViewHolder(binding)
        } else {
            val binding = ItemChatLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LeftChatViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is RightChatViewHolder) {
            holder.bind(message)
        } else if (holder is LeftChatViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isSentByUser) VIEW_TYPE_RIGHT else VIEW_TYPE_LEFT
    }

    class RightChatViewHolder(private val binding: ItemChatRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.rightChat.text = message.message
        }
    }

    class LeftChatViewHolder(private val binding: ItemChatLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.leftChat.text = message.message
        }
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem == newItem
    }
}