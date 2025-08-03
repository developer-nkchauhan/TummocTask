package com.app.tummoctask.presentation.ui

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tummoctask.R
import com.app.tummoctask.databinding.ActivityMainBinding
import com.app.tummoctask.presentation.ui.adapter.VehicleAdapter
import com.app.tummoctask.presentation.viewmodel.VehicleViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var adapter: VehicleAdapter
    lateinit var viewModel : VehicleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        viewModel = ViewModelProvider(this)[VehicleViewModel::class.java]
        adapter = VehicleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allVehicles.observe(this) { vehicles ->
            vehicles?.let {
                if(it.isNotEmpty()) {
                    adapter.submitVehicleList(it)
                } else {
                    viewModel.addVehicleInitially()
                }
            }
        }

        viewModel.totalVehicles.observe(this) { count -> count?.let {
            binding.incWelcome.mtvTotalVehicle.text = it.toString()
        }}
        viewModel.electricVehicles.observe(this) { count -> count?.let {
            binding.incWelcome.mtvTotalEVVehicle.text = it.toString()
        }}

        binding.btnAddVehicle.setOnClickListener {
            val intent = Intent(this, AddVehicleActivity::class.java)
            startActivity(intent)
        }

        binding.btnFilter.setOnClickListener {
            FilterDialog { onSelected ->
                applyFilter(onSelected)
            }.show(supportFragmentManager,"FilterDialog")
        }
    }

    private fun applyFilter(onSelected:Map<String, List<String>>) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTotalVehiclesCount()
        viewModel.fetchElectricCount()
    }
}