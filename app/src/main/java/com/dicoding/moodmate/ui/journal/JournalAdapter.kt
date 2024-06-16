package com.dicoding.moodmate.ui.journal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.moodmate.data.response.JournalData
import com.dicoding.moodmate.databinding.ItemJournalBinding

class JournalAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    var listJournals = ArrayList<JournalData>()
        set(listJournals) {
            if (listJournals.size > 0) {
                this.listJournals.clear()
            }
            this.listJournals.addAll(listJournals)
            notifyDataSetChanged()
        }

    fun addItem(journal: JournalData) {
        this.listJournals.add(journal)
        notifyItemInserted(this.listJournals.size - 1)
    }

    fun updateItem(position: Int, journal: JournalData) {
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
        fun bind(journal: JournalData) {
            binding.tvItemTitle.text = journal.title
            binding.tvItemDate.text = journal.createdAt
            binding.tvItemFill.text = journal.text
            binding.cvItemJournal.setOnClickListener {
                onItemClickCallback.onItemClicked(journal, adapterPosition)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedJournal: JournalData, position: Int?)
    }
}
