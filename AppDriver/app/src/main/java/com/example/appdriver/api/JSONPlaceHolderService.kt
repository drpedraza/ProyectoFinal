package com.example.appdriver.api

import com.example.appdriver.models.Driver
import com.example.appdriver.models.Orders
import com.example.appdriver.models.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface JSONPlaceHolderService {

    @POST("users")
    fun registerDriver(@Body driver: Driver): Call<Driver>

    @POST("users/login")
    fun loginDriver(@Body credentials: Map<String, String>): Call<Token>

    @GET("orders/free")
    fun getOrders(@Header("Authorization") token: String): Call<Orders>
}