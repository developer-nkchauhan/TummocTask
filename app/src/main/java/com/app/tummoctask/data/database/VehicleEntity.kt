package com.app.tummoctask.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val model: String = "",
    val brand: String = "",
    val number: String = "",
    val fuelType: String = "",
    val yearOfPurchase: String = "",
    val ownerName : String = ""
)
