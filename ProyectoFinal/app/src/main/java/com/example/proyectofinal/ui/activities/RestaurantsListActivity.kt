package com.example.proyectofinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ActivityRestaurantsListBinding
import com.example.proyectofinal.ui.adapters.RestaurantAdapter
import com.example.proyectofinal.ui.viewmodels.RestaurantViewModel

class RestaurantsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantsListBinding
    private val restaurantViewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRestaurantsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupEventListeners()

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            restaurantViewModel.fetchRestaurants(token)
        } else {
            Toast.makeText(this, "Token no encontrado. Inicie sesión.", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupEventListeners() {
        binding.fabOrderList.setOnClickListener {
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvRestaurantList.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        restaurantViewModel.restaurants.observe(this, Observer { restaurants ->
            val adapter = RestaurantAdapter(restaurants)
            binding.rvRestaurantList.adapter = adapter
        })

        restaurantViewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }
}
