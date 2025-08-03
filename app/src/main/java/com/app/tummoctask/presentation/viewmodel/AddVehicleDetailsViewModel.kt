package com.app.tummoctask.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.tummoctask.R
import com.app.tummoctask.data.PickerItem
import com.app.tummoctask.data.VehicleDatabase
import com.app.tummoctask.data.VehicleEntity
import com.app.tummoctask.domain.repository.VehicleRepository
import kotlinx.coroutines.launch

class AddVehicleDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private var repo : VehicleRepository

    init {
        val dao = VehicleDatabase.getDatabase(application).vehicleDao()
        repo = VehicleRepository(dao)
    }

    fun addVehicleToDb(vehicle : VehicleEntity) = viewModelScope.launch {
        repo.insert(vehicle)
    }

    fun getBrandItems() : List<PickerItem> {
        return listOf(
            PickerItem("Tata", R.drawable.ic_tata),
            PickerItem("Honda", R.drawable.ic_honda),
            PickerItem("Hero", R.drawable.ic_hero),
            PickerItem("Bajaj", R.drawable.ic_bajaj),
            PickerItem("Yamaha", R.drawable.ic_yamaha)
        )
    }

    fun getModelNameByBrandName(modelName : String) : List<PickerItem> {
        val brandModelMap: HashMap<String, List<PickerItem>> = hashMapOf(
            "tata" to listOf(
                PickerItem(name = "Punch"),
                PickerItem(name = "Nexon"),
                PickerItem(name = "Harrier"),
                PickerItem(name = "Tiago"),
                PickerItem(name = "Altroz")
            ),
            "honda" to listOf(
                PickerItem(name = "City"),
                PickerItem(name = "Amaze"),
                PickerItem(name = "Jazz"),
                PickerItem(name = "WR-V"),
                PickerItem(name = "Elevate")
            ),
            "hero" to listOf(
                PickerItem(name = "Splendor"),
                PickerItem(name = "HF Deluxe"),
                PickerItem(name = "Passion Pro"),
                PickerItem(name = "Glamour"),
                PickerItem(name = "Xtreme")
            ),
            "bajaj" to listOf(
                PickerItem(name = "Pulsar"),
                PickerItem(name = "Avenger"),
                PickerItem(name = "Platina"),
                PickerItem(name = "CT 100"),
                PickerItem(name = "Dominar")
            ),
            "yamaha" to listOf(
                PickerItem(name = "FZ"),
                PickerItem(name = "R15"),
                PickerItem(name = "MT 15"),
                PickerItem(name = "Fascino"),
                PickerItem(name = "Ray ZR")
            ),
            "other" to listOf(
                PickerItem(name = "Model A"),
                PickerItem(name = "Model B"),
                PickerItem(name = "Model C")
            )
        )

        return brandModelMap[modelName.lowercase()] ?: emptyList()
    }
}

