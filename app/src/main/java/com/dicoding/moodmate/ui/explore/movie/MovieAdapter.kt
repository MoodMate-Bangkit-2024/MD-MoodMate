package com.dicoding.moodmate.ui.explore.movie

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.moodmate.R
import com.dicoding.moodmate.data.response.MovieItem
import com.dicoding.moodmate.databinding.ItemMovieBinding

class MovieAdapter : ListAdapter<MovieItem, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MovieViewHolder(private val bind : ItemMovieBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(item: MovieItem) {
            bind.movieName.text = item.title
            if (item.imageUrl.isNullOrEmpty()) {
                bind.ivMovie.setImageResource(R.drawable.default_img)
            } else {
                Glide.with(bind.root)
                    .load(item.imageUrl)
                    .into(bind.ivMovie)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(
                oldItem: MovieItem,
                newItem: MovieItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MovieItem,
                newItem: MovieItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}