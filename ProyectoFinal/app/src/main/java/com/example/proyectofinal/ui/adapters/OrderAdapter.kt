package com.example.proyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.OrderDetail

class OrderAdapter(
    private val orderList: ArrayList<OrderDetail>,
    private val onCartUpdated: () -> Unit
):
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productTextView: TextView = itemView.findViewById(R.id.txtProductName)
        val priceTextView: TextView = itemView.findViewById(R.id.txtProductPrice)
        val qtyTextView: TextView = itemView.findViewById(R.id.txtQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.productTextView.text = order.name
        holder.priceTextView.text = "Bs. ${order.price}"
        holder.qtyTextView.text = "Cantidad: ${order.qty}"

        // Notifica cambios al carrito si hay alguna acción adicional (como eliminar)
        holder.itemView.setOnClickListener {
            order.qty += 1 // Ejemplo de cambio en la cantidad
            notifyItemChanged(position)
            onCartUpdated() // Notificar a la actividad
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}