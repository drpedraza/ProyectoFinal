package com.example.proyectofinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ActivityRegisterBinding
import com.example.proyectofinal.models.User
import com.example.proyectofinal.ui.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.fabSaveRegister.setOnClickListener {
            val user = User(
                name = binding.txtUserRegister.text.toString(),
                email = binding.txtEmailRegister.text.toString(),
                password = binding.txtPasswordRegister.text.toString(),
                role = 1
            )
            println(user)
            viewModel.registerUser(user, onSuccess = {
                Toast.makeText(this, "Usuario registrado con éxito.", Toast.LENGTH_SHORT).show()
                finish()
            }, onError = {
                Toast.makeText(this, "Error al crear un usuario.", Toast.LENGTH_SHORT).show()
            })
        }
    }

    companion object {
        fun newIntent(mainActivity: MainActivity): Intent {
            return Intent(mainActivity, RegisterActivity::class.java)
        }
    }
}