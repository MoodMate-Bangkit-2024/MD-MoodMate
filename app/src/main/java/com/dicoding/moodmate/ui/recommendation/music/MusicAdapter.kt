package com.dicoding.moodmate.ui.recommendation.music

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.data.response.MusicItem
import com.dicoding.moodmate.databinding.ItemMusicBinding

class MusicAdapter : ListAdapter<MusicItem, MusicAdapter.MusicViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MusicViewHolder(private val binding : ItemMusicBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MusicItem) {
            binding.tvMusicTitle.text = item.title
            binding.tvSinger.text = item.singer

            binding.btnListen.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicItem>() {
            override fun areItemsTheSame(
                oldItem: MusicItem,
                newItem: MusicItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MusicItem,
                newItem: MusicItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
