package com.example.proyectofinal.models

data class OrderDetail(
    val product_id: Int,
    val name : String = "",
    var qty: Int,
    val price: String,
)