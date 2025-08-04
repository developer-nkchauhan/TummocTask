package com.app.tummoctask.presentation.ui.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tummoctask.R
import com.app.tummoctask.data.PickerItem
import com.app.tummoctask.databinding.BottomSheetPickerBinding
import com.app.tummoctask.databinding.ItemPickerOptionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOptionPicker(
    private val title : String,
    private val items : List<PickerItem>,
    private val selectedItem : String?,
    private val onOptionSelected: (String) -> Unit
) : BottomSheetDialogFragment() {

    lateinit var binding : BottomSheetPickerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPickerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = title
        val adapter = PickerAdapter(items, selectedItem) { item ->
            onOptionSelected(item.name)
            dismiss()
        }
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter
    }


    class PickerAdapter(
        private val items: List<PickerItem>,
        private var selected: String?,
        private val onSelect: (PickerItem) -> Unit
    ) : RecyclerView.Adapter<PickerAdapter.ItemViewHolder>() {

        inner class ItemViewHolder(val binding: ItemPickerOptionBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPickerOptionBinding.inflate(inflater, parent, false)
            return ItemViewHolder(binding)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = items[position]
            holder.binding.tvItemName.text = item.name
            holder.binding.radioSelect.isChecked = item.name == selected

            if (item.iconRes != null) {
                holder.binding.ivIcon.setImageResource(item.iconRes)
                holder.binding.ivIcon.visibility = View.VISIBLE
            } else {
                holder.binding.ivIcon.visibility = View.GONE
            }

            holder.binding.root.setOnClickListener {
                selected = item.name
                notifyDataSetChanged()
                onSelect(item)
            }

            holder.binding.root.setBackgroundResource(
                if (item.name == selected) R.drawable.bg_selected_option
                else R.drawable.bg_unselected_option
            )
        }

        override fun getItemCount() = items.size
    }
}