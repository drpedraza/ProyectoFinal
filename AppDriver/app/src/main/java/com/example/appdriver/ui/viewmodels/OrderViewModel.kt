package com.example.appdriver.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdriver.models.Orders
import com.example.appdriver.repositories.OrderRepository

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<Orders>().apply {
        value = arrayListOf()
    }
    val orders = _orders

    fun getOrders(token: String) {
        OrderRepository.getOrders(
            token,
            onSuccess = {
                _orders.value = it
            },
            onError = {
                println("Error: ${it.message}")
            })
    }


}