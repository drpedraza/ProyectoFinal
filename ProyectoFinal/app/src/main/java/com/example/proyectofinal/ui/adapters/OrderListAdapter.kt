package com.example.proyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.OrderMe

class OrderListAdapter(private val orders: List<OrderMe>) :
    RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtIdOrderList: TextView = itemView.findViewById(R.id.txtIdOrderList)
        val txtResturanteOrderList: TextView = itemView.findViewById(R.id.txtResturanteOrderList)
        val txtTotalOrderList: TextView = itemView.findViewById(R.id.txtTotalOrderList)
        val txtStatusOrderList: TextView = itemView.findViewById(R.id.txtStatusOrderList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_list_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.txtIdOrderList.text = order.id.toString()
        holder.txtResturanteOrderList.text = order.restaurant_id.toString()

        // Mostrar el precio con prefijo "Bs."
        holder.txtTotalOrderList.text = "Bs. ${order.total}"

        // Asignar el texto según el estado
        holder.txtStatusOrderList.text = when (order.status.toInt()) {
            1 -> "Solicitado"
            2 -> "Aceptado"
            3 -> "En camino"
            4 -> "Finalizado"
            else -> "Desconocido"
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
