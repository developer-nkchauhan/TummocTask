package com.app.tummoctask.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tummoctask.data.FilterOption
import com.app.tummoctask.databinding.ItemFilterOptionsBinding

class OptionAdapter(
    private val options: List<FilterOption>,
    private val onCheckedChange: (name: String, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<OptionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFilterOptionsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterOptionsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.binding.option = option

        // Prevent re-triggering old listener
        holder.binding.checkbox.setOnCheckedChangeListener(null)
        holder.binding.checkbox.isChecked = option.isSelected

        holder.binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(option.name, isChecked)
        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = options.size
}
