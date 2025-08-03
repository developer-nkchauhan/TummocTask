package com.app.tummoctask.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.tummoctask.R
import com.app.tummoctask.data.PickerItem
import com.app.tummoctask.data.VehicleEntity
import com.app.tummoctask.databinding.ActivityAddVehicleBinding
import com.app.tummoctask.presentation.viewmodel.AddVehicleDetailsViewModel

class AddVehicleActivity : AppCompatActivity() {

    lateinit var bind : ActivityAddVehicleBinding

    lateinit var viewmodel : AddVehicleDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = DataBindingUtil.setContentView(this,R.layout.activity_add_vehicle)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        ViewCompat.setOnApplyWindowInsetsListener(bind.clRootAddVehicle) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, systemBars.bottom)
            insets
        }

        val fuelItems = listOf(
            PickerItem("Petrol"),
            PickerItem("Diesel"),
            PickerItem("Electric"),
            PickerItem("Hybrid")
        )

        bind.actBrand.setOnClickListener { showBrandPickerDialog(viewmodel.getBrandItems()) }

        bind.actModel.setOnClickListener {
            val brandName = bind.actBrand.text.toString()
            if(brandName.isNotEmpty()) {
                showModelPickerDialog(brandName)
            } else {
                Toast.makeText(this@AddVehicleActivity,"No brand found", Toast.LENGTH_SHORT).show()
            }
        }

        bind.actFuelType.setOnClickListener {
            val modelName = bind.actModel.text.toString()
            if(modelName.isNotEmpty()) {
                showFuelTypePickerDialog(fuelItems)
            } else {
                Toast.makeText(this@AddVehicleActivity,"Select model Name first..", Toast.LENGTH_SHORT).show()
            }
        }

        bind.btnAddVehicle.setOnClickListener {
            val brandName = bind.actBrand.text.toString()
            val modelName = bind.actModel.text.toString()
            val fuelType = bind.actFuelType.text.toString()
            val vehicleNo = bind.etVehicleNumber.text.toString()

            if(brandName.isEmpty() || modelName.isEmpty() || fuelType.isEmpty() || vehicleNo.isEmpty()) {
                Toast.makeText(this,"Vehicle details can't be empty..", Toast.LENGTH_SHORT).show()
            } else {
                val vehicle = VehicleEntity(
                    model = modelName,
                    brand = brandName,
                    fuelType = fuelType,
                    yearOfPurchase = bind.etYear.text.toString(),
                    ownerName = bind.etOwner.text.toString(),
                    number = vehicleNo
                )
                viewmodel.addVehicleToDb(vehicle)
                Toast.makeText(this,"Vehicle added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewmodel = ViewModelProvider(this)[AddVehicleDetailsViewModel::class.java]
    }

    private fun showFuelTypePickerDialog(fuelItems: List<PickerItem>) {
        BottomSheetOptionPicker(
            title = "Select Fuel Type",
            items = fuelItems,
            selectedItem = bind.fuelLayout.editText?.text.toString()
        ) { selectedFuelType ->
            bind.actFuelType.setText(selectedFuelType,false)
        }.show(supportFragmentManager,"FuelPicker")
    }

    private fun showModelPickerDialog(brandName: String) {
        BottomSheetOptionPicker(
            title = "Select Model",
            items = viewmodel.getModelNameByBrandName(brandName),
            selectedItem = bind.modelLayout.editText?.text.toString()
        ) { selectedBrand ->
            bind.actModel.setText(selectedBrand,false)

        }.show(supportFragmentManager,"ModelPicker")
    }

    private fun showBrandPickerDialog(brandItems: List<PickerItem>) {
        BottomSheetOptionPicker(
            title = "Select Brand",
            items = brandItems,
            selectedItem = bind.brandLayout.editText?.text.toString()
        ) { selectedBrand ->
            bind.actBrand.setText(selectedBrand,false)

        }.show(supportFragmentManager,"BrandPicker")
    }


}