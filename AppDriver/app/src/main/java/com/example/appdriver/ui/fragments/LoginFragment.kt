package com.example.appdriver.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appdriver.R
import com.example.appdriver.databinding.FragmentLoginBinding
import com.example.appdriver.repositories.DriverRepository

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmailLogin.text.toString()
            val password = binding.txtPasswordLogin.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Por favor, ingrese su correo y contraseña.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            DriverRepository.loginDriver(email, password,
                onSuccess = { token ->
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                    val sharedPreferences = requireContext().getSharedPreferences("driver", AppCompatActivity.MODE_PRIVATE)
                    sharedPreferences.edit().putString("token", token).apply()

                    val navController = findNavController()
                    navController.navigate(R.id.action_loginFragment_to_orderFragment)

                },
                onError = {
                    Toast.makeText(requireContext(), "Error al iniciar sesión.", Toast.LENGTH_SHORT).show()
                }
            )
        }
        binding.btnRegister.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
