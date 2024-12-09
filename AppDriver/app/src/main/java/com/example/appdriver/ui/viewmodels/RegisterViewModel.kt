package com.example.appdriver.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.appdriver.models.Driver
import com.example.appdriver.repositories.DriverRepository

class RegisterViewModel : ViewModel() {

    fun registerDriver(driver: Driver, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        DriverRepository.registerDriver(driver,
            onSuccess = {
                onSuccess()
            }, onError = {
                onError(it)
            })
    }
}