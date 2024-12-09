package com.example.appdriver.models

data class Order(
    val id: Int,
    val name: String,
    val restaurant_id: Int,
    val address: String,
    val status: String
)
typealias Orders = ArrayList<Order>
