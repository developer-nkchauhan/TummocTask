package com.app.tummoctask.domain.repository

import androidx.lifecycle.LiveData
import com.app.tummoctask.data.database.VehicleDao
import com.app.tummoctask.data.database.VehicleEntity

class VehicleRepository(private val dao : VehicleDao) {

    val allVehicles : LiveData<List<VehicleEntity>> = dao.getAllVehicles()
    suspend fun insert(vehicle : VehicleEntity) = dao.insert(vehicle)
    suspend fun getTotalVehicles() : Int = dao.getTotalVehicleCount()
    suspend fun getTotalElectricVehicles(): Int = dao.getElectricVehicleCount()

    suspend fun getBrands() : List<String> = dao.getAllDistinctBrands()
    suspend fun getFilteredQueryData(brands : List<String>?,fTypes : List<String>?) : List<VehicleEntity> = dao.getFilteredProducts(brands,fTypes)

}