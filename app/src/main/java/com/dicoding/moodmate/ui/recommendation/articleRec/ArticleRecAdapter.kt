package com.dicoding.moodmate.ui.recommendation.articleRec

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.data.response.ArticleRecItem
import com.dicoding.moodmate.databinding.ItemRecArticleBinding

class ArticleRecAdapter : ListAdapter<ArticleRecItem, ArticleRecAdapter.ArticleRecViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleRecViewHolder {
        val binding = ItemRecArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleRecViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleRecViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ArticleRecViewHolder(private val binding : ItemRecArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ArticleRecItem) {
            binding.tvArticleTitle.text = item.title
            binding.tvArticleAuthor.text = item.author

            binding.btnReadMore.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleRecItem>() {
            override fun areItemsTheSame(
                oldItem: ArticleRecItem,
                newItem: ArticleRecItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: ArticleRecItem,
                newItem: ArticleRecItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
