package com.example.proyectofinal.api

import com.example.proyectofinal.models.Order
import com.example.proyectofinal.models.Orders
import com.example.proyectofinal.models.Restaurant
import com.example.proyectofinal.models.Restaurants
import com.example.proyectofinal.models.Token
import com.example.proyectofinal.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface JSONPlaceHolderService {

    @POST("users")
    fun registerUser(@Body user: User): Call<User>

    @POST("users/login")
    fun loginUser(@Body credentials: Map<String, String>): Call<Token>

    @GET("restaurants")
    fun getRestaurants(@Header("Authorization") token: String): Call<Restaurants>

    @GET("restaurants/{id}")
    fun getProductByRestaurant(@Header("Authorization") token: String, @Path("id") id: Int): Call<Restaurant>

    @POST("orders")
    fun createOrders(@Header("Authorization") token: String, @Body order: Order): Call<Order>

    @GET("orders")
    fun getOrders(@Header("Authorization") token: String): Call<Orders>
}