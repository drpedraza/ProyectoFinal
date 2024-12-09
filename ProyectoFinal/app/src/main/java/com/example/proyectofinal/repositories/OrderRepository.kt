package com.example.proyectofinal.repositories

import com.example.proyectofinal.api.JSONPlaceHolderService
import com.example.proyectofinal.models.Order
import com.example.proyectofinal.models.Orders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OrderRepository {

    fun getOrders(
        token: String,
        onSuccess: (Orders) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.getOrders("Bearer $token").enqueue(object : Callback<Orders> {
            override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    onSuccess(orders!!)
                }
            }

            override fun onFailure(call: Call<Orders>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun createOrders(
        token: String,
        order: Order,
        onSuccess: (Order) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.createOrders("Bearer $token", order).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    onSuccess(orders!!)
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }
}