package com.example.proyectofinal.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ActivityOrderListBinding
import com.example.proyectofinal.ui.adapters.OrderListAdapter
import com.example.proyectofinal.ui.viewmodels.OrderViewModel

class OrderListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderListBinding
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            orderViewModel.fetchOrders(token)
        } else {
            Toast.makeText(this, "Token no encontrado. Inicie sesión.", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        binding.rvOrderList.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        orderViewModel.orders.observe(this, Observer { orders ->
            val adapter = OrderListAdapter(orders)
            binding.rvOrderList.adapter = adapter
        })

        orderViewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }
}
