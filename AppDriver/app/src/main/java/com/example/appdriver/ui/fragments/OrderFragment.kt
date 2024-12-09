package com.example.appdriver.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdriver.R
import com.example.appdriver.databinding.FragmentOrderBinding
import com.example.appdriver.ui.adapter.OrderAdapter
import com.example.appdriver.ui.viewmodels.OrderViewModel

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOrderList.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val sharedPreferences = requireContext().getSharedPreferences("driver", AppCompatActivity.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "") ?: ""

        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            val adapter = OrderAdapter(orders)
            binding.rvOrderList.adapter = adapter
        }

        viewModel.getOrders(token)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
