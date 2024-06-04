package com.dicoding.moodmate.ui.explore

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.R
import com.dicoding.moodmate.ui.explore.article.ArticleActivity

class ExploreAdapter(private val listExplore: ArrayList<Explore>) : RecyclerView.Adapter<ExploreAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_explore, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo) = listExplore[position]
        holder.tvName.text = name
        holder.imgPhoto.setImageResource(photo)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, ArticleActivity::class.java)
            intentDetail.putExtra("key_movie", listExplore[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listExplore.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_explore)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}
