package com.example.appdriver.repositories

import com.example.appdriver.api.JSONPlaceHolderService
import com.example.appdriver.models.Driver
import com.example.appdriver.models.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DriverRepository {
    fun registerDriver(
        driver: Driver,
        onSuccess: (Driver) -> Unit,
        onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.registerDriver(driver).enqueue(object : Callback<Driver> {
            override fun onResponse(call: Call<Driver>, response: Response<Driver>) {
                if (response.isSuccessful) {
                    val registerDriver = response.body()
                    onSuccess(registerDriver!!)
                }
            }

            override fun onFailure(call: Call<Driver>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun loginDriver(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        val credentials = mapOf("email" to email, "password" to password)

        service.loginDriver(credentials).enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    val token = response.body()
                    onSuccess(token!!.access_token)
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }
}