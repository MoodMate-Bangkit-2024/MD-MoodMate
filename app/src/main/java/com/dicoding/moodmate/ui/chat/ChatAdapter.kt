package com.dicoding.moodmate.ui.chat

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.data.pref.ChatModel
import com.dicoding.moodmate.databinding.ItemChatLeftBinding
import com.dicoding.moodmate.databinding.ItemChatRightBinding

class ChatAdapter(
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listOfChat = mutableListOf<ChatModel>()

    companion object {
        private const val VIEW_TYPE_LEFT = 1
        private const val VIEW_TYPE_RIGHT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.d("ChatAdapter", viewType.toString())
        return when (viewType) {
            VIEW_TYPE_RIGHT -> {
                val binding = ItemChatRightBinding.inflate(inflater, parent, false)
                RightViewHolder(binding)
            }
            else -> {
                val binding = ItemChatLeftBinding.inflate(inflater, parent, false)
                LeftViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = listOfChat.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (listOfChat[position].type == "SEND")
            (holder as RightViewHolder).bind(listOfChat[position])
        else
            (holder as LeftViewHolder).bind(listOfChat[position])
    }


    override fun getItemViewType(position: Int): Int {
        return if (listOfChat[position].type == "SEND") VIEW_TYPE_RIGHT else VIEW_TYPE_LEFT
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ChatModel>) {
        listOfChat.clear()
        listOfChat.addAll(list)
        notifyDataSetChanged()
    }

    inner class LeftViewHolder(private val binding: ItemChatLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatModel) {
            binding.leftChat.text = chat.message
        }
    }

    inner class RightViewHolder(private val binding: ItemChatRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatModel) {
            binding.rightChat.text = chat.message
        }
    }

}