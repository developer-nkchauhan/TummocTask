package com.app.tummoctask.presentation.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tummoctask.R
import com.app.tummoctask.databinding.ActivityMainBinding
import com.app.tummoctask.presentation.ui.dialogs.FilterDialog
import com.app.tummoctask.presentation.ui.adapter.TableDividerDecoration
import com.app.tummoctask.presentation.ui.adapter.VehicleAdapter
import com.app.tummoctask.presentation.viewmodel.FilterViewModel
import com.app.tummoctask.presentation.viewmodel.VehicleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var adapter: VehicleAdapter
    lateinit var viewModel : VehicleViewModel

    private lateinit var filterViewModel : FilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // MainActivitie's viewmodel
        viewModel = ViewModelProvider(this)[VehicleViewModel::class.java]
        // SharedViewModel to use between MainActivity and FilterDialog
        filterViewModel = ViewModelProvider(this)[FilterViewModel::class.java]

        // Vehicle Details adapter
        adapter = VehicleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(TableDividerDecoration(this, Color.GRAY,1,4))

        // observing vehicle being fetched from Database
        viewModel.allVehicles.observe(this) { vehicles ->
            vehicles?.let {
                if(it.isNotEmpty()) {
                    adapter.submitVehicleList(it)
                } else {
                    // initially adding manual data to db
                    viewModel.addVehicleInitially()
                }
            }
        }

        // Managing count of total vehicles and electric vehicles
        viewModel.totalVehicles.observe(this) { count -> count?.let {
            binding.incWelcome.mtvTotalVehicle.text = it.toString()
        }}
        viewModel.electricVehicles.observe(this) { count -> count?.let {
            binding.incWelcome.mtvTotalEVVehicle.text = it.toString()
        }}

        binding.btnAddVehicle.setOnClickListener {
            // clearing filter when user is navigating to add vehicle screen
            filterViewModel.clearAllSelections()
            // starts new screen to add details about vehicles..
            val intent = Intent(this, AddVehicleActivity::class.java)
            startActivity(intent)
        }

        binding.btnFilter.setOnClickListener {
            try {
                // passing lambda function for click event of apply and clear from FilterDialog
                FilterDialog { isAppliedClicked ->
                    if (isAppliedClicked) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val selectedBrands = filterViewModel.getSelectedOptions("Brand")
                            val selectedFuelTypes = filterViewModel.getSelectedOptions("Fuel Type")
                            val result =
                                viewModel.getFilteredData(selectedBrands, selectedFuelTypes)
                            withContext(Dispatchers.Main) {
                                adapter.submitVehicleList(result)
                            }
                        }
                    } else {
                        val allVehicles = viewModel.allVehicles.value
                        if (allVehicles != null && allVehicles.isNotEmpty()) {
                            adapter.submitVehicleList(allVehicles)
                        }
                    }

                }.show(supportFragmentManager,"FilterDialog")
            }catch (e : Exception) {
                // we can also logs it to firebase but here it's not configured
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // fetching vehicles count
        viewModel.fetchTotalVehiclesCount()
        viewModel.fetchElectricCount()
    }
}