package com.example.proyectofinal.repositories

import com.example.proyectofinal.api.JSONPlaceHolderService
import com.example.proyectofinal.models.Token
import com.example.proyectofinal.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    fun registerUser(
        user: User,
        onSuccess: (User) -> Unit,
        onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        service.registerUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val registerUser = response.body()
                    onSuccess(registerUser!!)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("Error: ${t.message}")
                onError(t)
            }
        })
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(JSONPlaceHolderService::class.java)
        val credentials = mapOf("email" to email, "password" to password)

        service.loginUser(credentials).enqueue(object : Callback<Token> {
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