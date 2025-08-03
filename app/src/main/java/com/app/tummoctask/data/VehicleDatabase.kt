package com.app.tummoctask.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VehicleEntity::class], version = 1)
abstract class VehicleDatabase : RoomDatabase() {

    abstract fun vehicleDao() : VehicleDao

    companion object {
        @Volatile private var INSTANCE: VehicleDatabase? = null

        fun getDatabase(context: Context): VehicleDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDatabase::class.java,
                    "vehicle_db"
                ).build().also { INSTANCE = it }
            }
        }

    }
}