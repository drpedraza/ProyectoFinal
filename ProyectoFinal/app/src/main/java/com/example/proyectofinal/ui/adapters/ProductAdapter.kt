package com.example.proyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinal.R
import com.example.proyectofinal.models.OrderDetail
import com.example.proyectofinal.models.Product
import com.example.proyectofinal.ui.activities.ProductActivity

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtNameProduct: TextView = itemView.findViewById(R.id.txtNameProduct)
        val txtDescriptionProduct: TextView = itemView.findViewById(R.id.txtDescriptionProduct)
        val txtPriceProduct: TextView = itemView.findViewById(R.id.txtPriceProduct)
        val fabAddProduct: ImageView = itemView.findViewById(R.id.fabAddProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_detail_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.txtNameProduct.text = product.name
        holder.txtDescriptionProduct.text = product.description
        holder.txtPriceProduct.text = "Bs. ${product.price}"

        Glide.with(holder.itemView.context)
            .load(product.image)
            .into(holder.imgProduct)

        holder.fabAddProduct.setOnClickListener {
            val existingOrder = ProductActivity.cart.find { it.product_id == product.id }
            if (existingOrder != null) {
                // Incrementa la cantidad si el producto ya está en el carrito
                existingOrder.qty += 1
            } else {
                // Agrega el producto al carrito
                val order = OrderDetail(
                    product_id = product.id,
                    name = product.name,
                    qty = 1, // Cantidad inicial
                    price = product.price
                )
                ProductActivity.cart.add(order)
            }
            Toast.makeText(holder.itemView.context, "${product.name} agregado al carrito", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
