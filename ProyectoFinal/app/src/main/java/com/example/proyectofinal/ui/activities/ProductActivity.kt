package com.example.proyectofinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.databinding.ActivityProductBinding
import com.example.proyectofinal.models.OrderDetail
import com.example.proyectofinal.ui.adapters.ProductAdapter
import com.example.proyectofinal.ui.viewmodels.ProductViewModel

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private val productViewModel: ProductViewModel by viewModels()
    private var restaurant_id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurant_id = intent.getIntExtra("restaurant_id", -1)
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token != null && restaurant_id != -1) {
            setupRecyclerView()
            setupViewModel()
            productViewModel.getProductByRestaurant(token, restaurant_id)
            setupEventListeners()
        } else {
            Toast.makeText(this, "Error al cargar los productos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupEventListeners() {
        binding.fabOrder.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("restaurant_id", restaurant_id)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvProductsList.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        productViewModel.products.observe(this) { products ->
            val adapter = ProductAdapter(products)
            binding.rvProductsList.adapter = adapter
        }
    }
    companion object {
        val cart = ArrayList<OrderDetail>()
    }

}
