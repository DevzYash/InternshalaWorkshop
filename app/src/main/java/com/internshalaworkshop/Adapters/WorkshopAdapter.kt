package com.internshalaworkshop.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jokerhdwallpaper.internshalaworkshop.databinding.WorkshopItemBinding
import com.internshalaworkshop.models.WorkshopEntity

class WorkshopAdapter(private val onclickItem: (WorkshopEntity) -> Unit) :
    ListAdapter<WorkshopEntity, WorkshopAdapter.WorkshopViewHolder>(WorkshopDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder {
        val binding =
            WorkshopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkshopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        val workshop = getItem(position)
        holder.bind(workshop)
    }

    inner class WorkshopViewHolder(private val binding: WorkshopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workshop: WorkshopEntity) {
            binding.workshopName.text = workshop.workshopName
            binding.workshopDescription.text = workshop.description
            binding.workshopDate.text = workshop.date
            binding.root.setOnClickListener {
                onclickItem(workshop)
            }
        }
    }
}


class WorkshopDiffCallback : DiffUtil.ItemCallback<WorkshopEntity>() {
    override fun areItemsTheSame(oldItem: WorkshopEntity, newItem: WorkshopEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WorkshopEntity, newItem: WorkshopEntity): Boolean {
        return oldItem == newItem
    }
}
