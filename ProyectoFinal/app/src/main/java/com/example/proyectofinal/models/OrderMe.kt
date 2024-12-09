package com.example.proyectofinal.models

data class OrderMe(
    val id: Int,
    val restaurant_id: Int,
    val total: String,
    val status: String,
)
typealias Orders = ArrayList<OrderMe>