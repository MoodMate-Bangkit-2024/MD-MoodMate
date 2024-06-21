package com.dicoding.moodmate.ui.explore.article

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moodmate.R
import com.dicoding.moodmate.data.response.DataItem
import com.dicoding.moodmate.data.util.DateFormatter
import com.dicoding.moodmate.databinding.ItemArticleBinding
import java.util.TimeZone

class ArticleAdapter : ListAdapter<DataItem, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ArticleViewHolder(private val binding : ItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DataItem) {
            binding.articleName.text = item.title
            binding.descArticle.text = item.description
            binding.tvItemDate.text = item.publishedAt?.let { DateFormatter.formatDate(it, TimeZone.getDefault().id) }

            if (item.imageUrl.isNullOrEmpty()) {
                binding.ivPhoto.setImageResource(R.drawable.img_default)
            } else {
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default)
                    .into(binding.ivPhoto)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
