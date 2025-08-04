package com.app.tummoctask.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tummoctask.data.database.VehicleEntity
import com.app.tummoctask.databinding.ItemVehicleBinding
import com.app.tummoctask.databinding.ItemVehicleHeaderBinding
import com.app.tummoctask.presentation.ui.VehicleListItem

class VehicleAdapter : ListAdapter<VehicleListItem, RecyclerView.ViewHolder>(VehicleDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is VehicleListItem.Header -> VIEW_TYPE_HEADER
            is VehicleListItem.VehicleRow -> VIEW_TYPE_ITEM
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemVehicleHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_ITEM -> {
                val binding = ItemVehicleBinding.inflate(inflater, parent, false)
                VehicleViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is VehicleListItem.VehicleRow -> (holder as VehicleViewHolder).bind(item.vehicle)
            else -> null
        }
    }

    fun submitVehicleList(vehicles: List<VehicleEntity>) {
        val items = mutableListOf<VehicleListItem>(VehicleListItem.Header)
        items.addAll(vehicles.map { VehicleListItem.VehicleRow(it) })
        submitList(items)
    }

    class HeaderViewHolder(binding: ItemVehicleHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class VehicleViewHolder(private val binding: ItemVehicleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vehicle: VehicleEntity) {
            binding.vehicle = vehicle
            binding.executePendingBindings()
        }
    }

    class VehicleDiffCallback : DiffUtil.ItemCallback<VehicleListItem>() {
        override fun areItemsTheSame(oldItem: VehicleListItem, newItem: VehicleListItem): Boolean {
            return oldItem is VehicleListItem.VehicleRow &&
                    newItem is VehicleListItem.VehicleRow &&
                    oldItem.vehicle.id == newItem.vehicle.id
        }

        override fun areContentsTheSame(oldItem: VehicleListItem, newItem: VehicleListItem): Boolean {
            return oldItem == newItem
        }
    }

}