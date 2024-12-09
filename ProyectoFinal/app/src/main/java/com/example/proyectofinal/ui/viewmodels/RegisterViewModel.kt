package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.proyectofinal.models.User
import com.example.proyectofinal.repositories.UserRepository

class RegisterViewModel : ViewModel() {

    fun registerUser(user: User, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        UserRepository.registerUser(user,
            onSuccess = {
                onSuccess()
            }, onError = {
                onError(it)
            })
    }
}