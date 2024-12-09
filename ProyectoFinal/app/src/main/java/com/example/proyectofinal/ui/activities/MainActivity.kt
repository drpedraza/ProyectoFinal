package com.example.proyectofinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ActivityMainBinding
import com.example.proyectofinal.repositories.UserRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnRegister.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmailLogin.text.toString()
            val password = binding.txtPasswordLogin.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(
                    this,
                    "Por favor, ingrese su correo y contraseña.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            UserRepository.loginUser(email, password,
                onSuccess = { Token ->
                    Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                    val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                    sharedPreferences.edit().putString("token", Token).apply()

                    val intent = Intent(this, RestaurantsListActivity::class.java)
                    startActivity(intent)
                    finish()

                }, onError = {
                    Toast.makeText(this, "Error al iniciar sesión.", Toast.LENGTH_SHORT).show()
                })

        }
    }
}