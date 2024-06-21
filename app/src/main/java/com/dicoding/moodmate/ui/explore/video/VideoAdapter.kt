package com.dicoding.moodmate.ui.explore.video

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.moodmate.R
import com.dicoding.moodmate.data.response.VideoItem
import com.dicoding.moodmate.databinding.ItemVideoBinding

class VideoAdapter : ListAdapter<VideoItem, VideoAdapter.VideoViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class VideoViewHolder(private val bind : ItemVideoBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(item: VideoItem) {
            bind.videoName.text = item.title
            if (item.imageUrl.isNullOrEmpty()) {
                bind.ivVideo.setImageResource(R.drawable.img_default)
            } else {
                Glide.with(bind.root)
                    .load(item.imageUrl)
                    .into(bind.ivVideo)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoItem>() {
            override fun areItemsTheSame(
                oldItem: VideoItem,
                newItem: VideoItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: VideoItem,
                newItem: VideoItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}