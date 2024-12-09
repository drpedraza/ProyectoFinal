package com.example.proyectofinal.models

data class User (
    val name: String,
    val email: String,
    val password: String,
    val role: Int
)
typealias Users = ArrayList<User>