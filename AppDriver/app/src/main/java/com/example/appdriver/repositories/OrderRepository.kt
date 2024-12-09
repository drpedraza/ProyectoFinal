package com.example.appdriver.repositories

import com.example.appdriver.api.JSONPlaceHolderService
import com.example.appdriver.models.Orders
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
                    val orders = response.body()?.filter { it.status == "1" }
                    onSuccess(Orders().apply { addAll(orders ?: emptyList()) })
                }
            }

            override fun onFailure(call: Call<Orders>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }
}