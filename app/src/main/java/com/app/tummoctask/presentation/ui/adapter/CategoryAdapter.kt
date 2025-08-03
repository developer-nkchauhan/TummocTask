package com.app.tummoctask.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tummoctask.databinding.ItemFilterCategoryBinding

class CategoryAdapter(
    private val categories: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedCategory : String? = categories.firstOrNull()

    inner class ViewHolder(val binding: ItemFilterCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val category = categories[position]
        val isSelected = category == selectedCategory

        holder.binding.category = category
        holder.binding.isSelected = isSelected
        holder.binding.executePendingBindings()

        holder.binding.root.setOnClickListener {
            val currentPos = holder.bindingAdapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                selectedCategory = categories[currentPos]
                notifyDataSetChanged()
                onClick(categories[currentPos])
            }
        }
    }


    override fun getItemCount() = categories.size
}
