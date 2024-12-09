package com.example.appdriver.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appdriver.databinding.FragmentRegisterBinding
import com.example.appdriver.models.Driver
import com.example.appdriver.ui.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabSaveRegister.setOnClickListener {
            val driver = Driver(
                name = binding.txtUserRegister.text.toString(),
                email = binding.txtEmailRegister.text.toString(),
                password = binding.txtPasswordRegister.text.toString(),
                role = 2
            )

            viewModel.registerDriver(driver,
                onSuccess = {
                    Toast.makeText(requireContext(), "Conductor registrado con éxito.", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                },
                onError = {
                    Toast.makeText(requireContext(), "Error al crear un conductor.", Toast.LENGTH_SHORT).show()
                }
            )
        }

        binding.fabCancelRegister.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
