package com.app.tummoctask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.app.tummoctask.data.FilterOption

class FilterViewModel : ViewModel() {

    private val _filterState = mutableMapOf<String, MutableList<FilterOption>>()
    val filterState: Map<String, List<FilterOption>> = _filterState

    var categories : List<String> = listOf("Brand","Fuel Type")

    fun setCategoryOptions(category: String, options: List<String>) {
        if (!_filterState.containsKey(category)) {
            _filterState[category] = options.map { FilterOption(it) }.toMutableList()
        }
    }

    fun updateSelection(category: String, optionName: String, isSelected: Boolean) {
        _filterState[category]?.find { it.name == optionName }?.isSelected = isSelected
    }

    fun getSelectedOptions(category: String): List<String> {
        return _filterState[category]?.filter { it.isSelected }?.map { it.name } ?: emptyList()
    }

    fun clearAllSelections() {
        _filterState.values.forEach { list ->
            list.forEach { it.isSelected = false }
        }
    }

    fun getAllSelected(): Map<String, List<String>> {
        return _filterState.mapValues { entry ->
            entry.value.filter { it.isSelected }.map { it.name }
        }
    }
}