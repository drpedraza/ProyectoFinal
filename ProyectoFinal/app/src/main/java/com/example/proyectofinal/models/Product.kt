package com.example.proyectofinal.models

data class Product(
    val id : Int,
    val name : String,
    val description : String,
    val price : String,
    val restaurantID: Int,
    val image : String
)
typealias Products = ArrayList<Product>
