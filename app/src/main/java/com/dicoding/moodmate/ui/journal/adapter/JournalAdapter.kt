package com.dicoding.moodmate.ui.journal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.databinding.ItemJournalBinding
import com.dicoding.moodmate.ui.journal.entitiy.Journal

class JournalAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    var listJournals = ArrayList<Journal>()
        set(listJournals) {
            if (listJournals.size > 0) {
                this.listJournals.clear()
            }
            this.listJournals.addAll(listJournals)
            notifyDataSetChanged()
        }

    fun addItem(journal: Journal) {
        this.listJournals.add(journal)
        notifyItemInserted(this.listJournals.size - 1)
    }

    fun updateItem(position: Int, journal: Journal) {
        this.listJournals[position] = journal
        notifyItemChanged(position, journal)
    }

    fun removeItem(position: Int) {
        this.listJournals.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listJournals.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val binding = ItemJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        holder.bind(listJournals[position])
    }

    override fun getItemCount(): Int = this.listJournals.size

    inner class JournalViewHolder(private val binding: ItemJournalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: Journal) {
            binding.tvItemTitle.text = journal.title
            binding.tvItemDate.text = journal.date
            binding.tvItemFill.text = journal.description
            binding.cvItemJournal.setOnClickListener {
                onItemClickCallback.onItemClicked(journal, adapterPosition)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedJournal: Journal?, position: Int?)
    }
}
