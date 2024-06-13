package com.dicoding.moodmate.ui.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.R
import com.dicoding.moodmate.ui.explore.article.ArticleActivity
import com.dicoding.moodmate.ui.explore.movie.MovieActivity
import com.dicoding.moodmate.ui.explore.video.VideoActivity

class ExploreAdapter(private val listExplore: ArrayList<Explore>) : RecyclerView.Adapter<ExploreAdapter.ListViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setExploreData(data: List<Explore>) {
        listExplore.clear()
        listExplore.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_explore, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo) = listExplore[position]
        holder.tvName.text = name
        holder.imgPhoto.setImageResource(photo)
        holder.itemView.setOnClickListener {
            val intentDetail: Intent = when (position % 3) {
                0 -> Intent(holder.itemView.context, VideoActivity::class.java)
                1 -> Intent(holder.itemView.context, ArticleActivity::class.java)
                else -> Intent(holder.itemView.context, MovieActivity::class.java)
            }
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listExplore.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_explore)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}
