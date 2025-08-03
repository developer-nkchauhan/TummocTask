package com.app.tummoctask.presentation.ui

import com.app.tummoctask.data.VehicleEntity

sealed class VehicleListItem {
    object Header : VehicleListItem()
    data class VehicleRow(val vehicle: VehicleEntity) : VehicleListItem()
}
