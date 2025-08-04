package com.app.tummoctask.data.database

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

    @Query("""
        SELECT * FROM vehicles
        WHERE brand IN (:brands)
        OR fuelType IN (:fuelTypes)
    """)
    suspend fun getFilteredProducts(
        brands: List<String>?,
        fuelTypes: List<String>?
    ): List<VehicleEntity>
}