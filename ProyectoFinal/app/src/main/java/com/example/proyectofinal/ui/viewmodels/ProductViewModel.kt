package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.models.Product
import com.example.proyectofinal.repositories.RestaurantRepository

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>().apply {
        value = emptyList()
    }
    val products: LiveData<List<Product>> = _products

    fun getProductByRestaurant(token: String, id: Int) {
        RestaurantRepository.getProductByRestaurant(token, id,
            onSuccess = {
                _products.value = it.products
            }, onError = {
                it.printStackTrace()
            })
    }
}