package com.dicoding.moodmate.ui.recommendation.movieRec

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.moodmate.R
import com.dicoding.moodmate.data.response.MovieRecItem
import com.dicoding.moodmate.databinding.ItemRecMovieBinding

class MovieRecAdapter: ListAdapter<MovieRecItem, MovieRecAdapter.MovieRecViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecViewHolder {
        val binding = ItemRecMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieRecViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieRecViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MovieRecViewHolder(private val bind : ItemRecMovieBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(item: MovieRecItem) {
            bind.movieRecName.text = item.title
            if (item.imageUrl.isNullOrEmpty()) {
                bind.ivRecMovie.setImageResource(R.drawable.img_default)
            } else {
                Glide.with(bind.root)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(bind.ivRecMovie)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieRecItem>() {
            override fun areItemsTheSame(
                oldItem: MovieRecItem,
                newItem: MovieRecItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MovieRecItem,
                newItem: MovieRecItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}