package com.app.tummoctask.presentation.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tummoctask.databinding.LayoutFilterBinding
import com.app.tummoctask.presentation.ui.adapter.CategoryAdapter
import com.app.tummoctask.presentation.ui.adapter.OptionAdapter
import com.app.tummoctask.presentation.viewmodel.FilterViewModel
import com.app.tummoctask.presentation.viewmodel.VehicleViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class FilterDialog(private val OnApplyOrClearClicked : (Boolean) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var filterViewModel: FilterViewModel
    private lateinit var vehicleViewModel : VehicleViewModel

    private var currentCategory = "Brand"

    lateinit var binding: LayoutFilterBinding

    private lateinit var optionAdapter: OptionAdapter
    private lateinit var categoryAdapter: CategoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutFilterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterViewModel = ViewModelProvider(requireActivity())[FilterViewModel::class.java]
        vehicleViewModel = ViewModelProvider(requireActivity())[VehicleViewModel::class.java]

        binding.btnApply.setOnClickListener {
            OnApplyOrClearClicked(true)
            dismiss()
        }

        binding.btnClearAll.setOnClickListener {
            filterViewModel.clearAllSelections()
            OnApplyOrClearClicked(false)
            dismiss()
        }

        categoryAdapter = CategoryAdapter(filterViewModel.categories) { category ->
            showOptionsInOptionsRecyclerView(category)
        }
        binding.rvFilterCategory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilterCategory.adapter = categoryAdapter

        lifecycleScope.launch {
            val brands = vehicleViewModel.getBrands()
            val fuels = vehicleViewModel.getFuelTypes()
            filterViewModel.setCategoryOptions("Brand",brands)
            filterViewModel.setCategoryOptions("Fuel Type",fuels)

            showOptionsInOptionsRecyclerView(currentCategory)
        }

    }

    private fun showOptionsInOptionsRecyclerView(selectedCategory: String) {
        currentCategory = selectedCategory
        val options = filterViewModel.filterState[selectedCategory]

        optionAdapter = OptionAdapter(options ?: emptyList()) { optionName, isChecked ->
            filterViewModel.updateSelection(currentCategory,optionName,isChecked)
        }

        binding.rvOptions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOptions.adapter = optionAdapter
    }
}