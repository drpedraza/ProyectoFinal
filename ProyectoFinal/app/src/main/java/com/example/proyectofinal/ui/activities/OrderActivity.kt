package com.example.proyectofinal.ui.activities

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.ui.adapters.OrderAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.proyectofinal.databinding.ActivityOrderBinding
import com.example.proyectofinal.models.Order
import com.example.proyectofinal.models.OrderDetail
import com.example.proyectofinal.repositories.OrderRepository
import com.example.proyectofinal.ui.viewmodels.OrderViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var restaurant_id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        restaurant_id = intent.getIntExtra("restaurant_id", -1)

        setupRecyclerView()
        calculateTotal()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.fabMap.setOnClickListener {
            requestLocation()
        }
        binding.fabSaveOrder.setOnClickListener {
            saveOrder()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun saveOrder() {
        // Recuperar el token del usuario
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(
                this,
                "No se encontró el token. Por favor, inicia sesión.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Verificar si hay productos en el carrito
        if (ProductActivity.cart.isEmpty()) {
            Toast.makeText(
                this,
                "El carrito está vacío. Agrega productos antes de hacer un pedido.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Obtener ubicación y dirección
        val latitude = binding.txtLatitude.text.toString().removePrefix("Lat: ").trim()
        val longitude = binding.txtLongitude.text.toString().removePrefix("Lng: ").trim()
        val address = binding.txtAddress.text.toString().trim()

        if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Faltan datos de ubicación o dirección.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Construir la lista de detalles del pedido
        val orderDetails = ProductActivity.cart.map {
            OrderDetail(
                product_id = it.product_id,
                qty = it.qty,
                price = it.price
            )
        }

        // Calcular el total del pedido
        val total = ProductActivity.cart.sumOf { it.qty * it.price.toDouble() }.toInt()

        // Crear el objeto Order
        val order = Order(
            restaurant_id = restaurant_id,
            total = total,
            address = address,
            latitude = latitude,
            longitude = longitude,
            details = ArrayList(orderDetails)
        )

        // Llamar al repositorio para enviar el pedido
        OrderRepository.createOrders(
            token = token,
            order = order,
            onSuccess = {
                // Limpiar el carrito
                ProductActivity.cart.clear()

                // Notificar al adaptador que los datos cambiaron
                binding.rvOrderList.adapter?.notifyDataSetChanged()

                // Actualizar el total a 0
                calculateTotal()

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Pedido creado exitosamente.", Toast.LENGTH_SHORT).show()

                // Finalizar la actividad
                finish()
            },
            onError = {
                Toast.makeText(this, "Error al crear el pedido: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                binding.txtLatitude.text = "Lat: $latitude"
                binding.txtLongitude.text = "Lng: $longitude"
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error obteniendo ubicación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateTotal() {
        val total = ProductActivity.cart.sumOf { it.qty * it.price.toDouble() }
        binding.txtTotalItemOrder.text = "Bs. $total"
    }

    private fun setupRecyclerView() {
        binding.rvOrderList.layoutManager = LinearLayoutManager(this)
        val adapter = OrderAdapter(ProductActivity.cart, this::calculateTotal)
        binding.rvOrderList.adapter = adapter
        calculateTotal()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation()
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
