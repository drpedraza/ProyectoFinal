package com.example.proyectofinal.repositories

import com.example.proyectofinal.api.JSONPlaceHolderService
import com.example.proyectofinal.models.Restaurant
import com.example.proyectofinal.models.Restaurants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RestaurantRepository {

    fun getRestaurants(
        token: String,
        onSuccess: (Restaurants) -> Unit,
        onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.getRestaurants("Bearer $token").enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>, response: Response<Restaurants>) {
                if (response.isSuccessful) {
                    val restaurants = response.body()
                    onSuccess(restaurants!!)
                }
            }

            override fun onFailure(call: Call<Restaurants>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun getProductByRestaurant(
        token: String,
        id: Int,
        onSuccess: (Restaurant) -> Unit,
        onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.getProductByRestaurant("Bearer $token", id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    val restaurant = response.body()
                    onSuccess(restaurant!!)
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }
}