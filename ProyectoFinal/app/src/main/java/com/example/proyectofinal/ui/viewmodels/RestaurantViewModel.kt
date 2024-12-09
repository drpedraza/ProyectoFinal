package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.models.Restaurants
import com.example.proyectofinal.repositories.RestaurantRepository

class RestaurantViewModel : ViewModel() {

    private val _restaurants = MutableLiveData<Restaurants>()
    val restaurants: LiveData<Restaurants> get() = _restaurants

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRestaurants(token: String) {
        RestaurantRepository.getRestaurants(
            token,
            onSuccess = { fetchedRestaurants ->
                _restaurants.value = fetchedRestaurants
            },
            onError = { throwable ->
                _error.value = throwable.message
            }
        )
    }
}
