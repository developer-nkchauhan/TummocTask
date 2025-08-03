package com.app.tummoctask.domain.repository

import androidx.lifecycle.LiveData
import com.app.tummoctask.data.VehicleDao
import com.app.tummoctask.data.VehicleEntity

class VehicleRepository(private val dao : VehicleDao) {

    val allVehicles : LiveData<List<VehicleEntity>> = dao.getAllVehicles()

    suspend fun insert(vehicle : VehicleEntity) = dao.insert(vehicle)
    suspend fun delete(vehicle: VehicleEntity) = dao.delete(vehicle)

    suspend fun getTotalVehicles() : Int = dao.getTotalVehicleCount()
    suspend fun getTotalElectricVehicles(): Int = dao.getElectricVehicleCount()
}