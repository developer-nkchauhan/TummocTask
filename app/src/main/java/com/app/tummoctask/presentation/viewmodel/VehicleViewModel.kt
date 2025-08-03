package com.app.tummoctask.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.tummoctask.data.VehicleDatabase
import com.app.tummoctask.data.VehicleEntity
import com.app.tummoctask.domain.repository.VehicleRepository
import kotlinx.coroutines.launch

class VehicleViewModel(application: Application) : AndroidViewModel(application) {

    private var repo : VehicleRepository

    var allVehicles : LiveData<List<VehicleEntity>>

    private val _totalVehicles = MutableLiveData<Int?>(null)
    val totalVehicles: LiveData<Int?> = _totalVehicles

    private val _electricVehicles = MutableLiveData<Int?>(null)
    val electricVehicles: LiveData<Int?> = _electricVehicles


    init {
        val dao = VehicleDatabase.getDatabase(application).vehicleDao()
        repo = VehicleRepository(dao)
        allVehicles = repo.allVehicles
    }

    fun fetchElectricCount() {
        viewModelScope.launch {
            val count = repo.getTotalElectricVehicles()
            _electricVehicles.value = count
        }
    }

    fun fetchTotalVehiclesCount() {
        viewModelScope.launch {
            val count = repo.getTotalVehicles()
            _totalVehicles.value = count
        }
    }

    fun addVehicleInitially() = viewModelScope.launch {
        listOf(VehicleEntity(
            model = "Swift",
            brand = "Maruti",
            number = "GJ01AB1234",
            fuelType = "Petrol",
            yearOfPurchase = "2020",
            ownerName = ""
        ),
            VehicleEntity(
                model = "City",
                brand = "Honda",
                number = "MH12CD5678",
                fuelType = "Diesel",
                yearOfPurchase = "2018"
            ),
            VehicleEntity(
                model = "i20",
                brand = "Hyundai",
                number = "DL5CA0001",
                fuelType = "Petrol",
                yearOfPurchase = "2022"
            ),
            VehicleEntity(
                model = "Nexon",
                brand = "Tata",
                number = "KA03EF7654",
                fuelType = "Electric",
                yearOfPurchase = "2023"
            ),
            VehicleEntity(
                model = "EcoSport",
                brand = "Ford",
                number = "TN10XY2345",
                fuelType = "Diesel",
                yearOfPurchase = "2019"
            )
        ).forEach {
            repo.insert(it)
        }
    }
}