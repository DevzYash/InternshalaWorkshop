package com.internshalaworkshop.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jokerhdwallpaper.internshalaworkshop.databinding.WorkshopItemBinding
import com.internshalaworkshop.models.AppliedWorkshopEntity

class AppliedWorkshopAdapter(private val onclickItem: (AppliedWorkshopEntity) -> Unit) :
    ListAdapter<AppliedWorkshopEntity, AppliedWorkshopAdapter.WorkshopViewHolder>(
        AppliedWorkshopDiffCallback()
    ) {
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
        fun bind(appliedWorkshopEntity: AppliedWorkshopEntity) {
            binding.workshopName.text = appliedWorkshopEntity.workshopName
//            binding.workshopDescription.text = appliedWorkshopEntity.workshopDetails.description
//            binding.workshopDate.text = appliedWorkshopEntity.workshopDetails.date
            binding.root.setOnClickListener {
                onclickItem(appliedWorkshopEntity)
            }
        }
    }
}


class AppliedWorkshopDiffCallback : DiffUtil.ItemCallback<AppliedWorkshopEntity>() {
    override fun areItemsTheSame(oldItem: AppliedWorkshopEntity, newItem: AppliedWorkshopEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AppliedWorkshopEntity, newItem: AppliedWorkshopEntity): Boolean {
        return oldItem == newItem
    }
}
