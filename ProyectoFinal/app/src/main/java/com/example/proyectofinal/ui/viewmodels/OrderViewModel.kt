package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.models.Order
import com.example.proyectofinal.models.Orders
import com.example.proyectofinal.repositories.OrderRepository

class OrderViewModel : ViewModel() {

    private val _orders = MutableLiveData<Orders>()
    val orders: LiveData<Orders> get() = _orders

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchOrders(token: String) {
        OrderRepository.getOrders(
            token,
            onSuccess = {
                _orders.postValue(it)
            },
            onError = {
                _error.postValue(it.message)
            }
        )
    }

    fun createOrder(
        order: Order,
        token: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        OrderRepository.createOrders(token, order,
            onSuccess = {
                onSuccess()
            }, onError = {
                onError(it)
            })
    }

}