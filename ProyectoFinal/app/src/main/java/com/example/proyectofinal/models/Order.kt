package com.example.proyectofinal.models

data class Order(
    val restaurant_id: Int,
    val total: Int,
    val address: String,
    val latitude: String,
    val longitude: String,
    val details: ArrayList<OrderDetail>
)
