package com.example.appdriver.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdriver.R
import com.example.appdriver.models.Order

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.txtIdItemOrder)
        val restaurant: TextView = view.findViewById(R.id.txtRestaurantItemOrder)
        val address: TextView = view.findViewById(R.id.txtAddressItemOrder)
        val status: TextView = view.findViewById(R.id.txtStatusItemOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.id.text = order.id.toString()
        holder.restaurant.text = order.restaurant_id.toString()
        holder.address.text = order.address
        holder.status.text = order.status
    }

    override fun getItemCount(): Int = orders.size
}
