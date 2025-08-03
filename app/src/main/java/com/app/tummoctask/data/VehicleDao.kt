package com.app.tummoctask.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VehicleDao {

    @Query("SELECT * FROM vehicles ORDER BY yearOfPurchase DESC")
    fun getAllVehicles(): LiveData<List<VehicleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity)

    @Delete
    suspend fun delete(vehicle: VehicleEntity)

    @Query("SELECT COUNT(*) FROM vehicles")
    suspend fun getTotalVehicleCount(): Int

    @Query("SELECT COUNT(*) FROM vehicles WHERE fuelType = 'Electric'")
    suspend fun getElectricVehicleCount(): Int

    @Query("SELECT DISTINCT brand FROM vehicles")
    suspend fun getAllDistinctBrands(): List<String>
}