package com.example.proyectofinal.models

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val logo: String,
    val products: ArrayList<Product> = ArrayList()
)
typealias Restaurants = ArrayList<Restaurant>
