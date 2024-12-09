package com.example.proyectofinal.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Restaurant
import com.example.proyectofinal.ui.activities.ProductActivity

class RestaurantAdapter(private val restaurantList: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.txtNameItemRestaurant)
        val addressTextView: TextView = itemView.findViewById(R.id.txtAddressItemRestaurant)
        val logoImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_item_layout, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.nameTextView.text = restaurant.name
        holder.addressTextView.text = restaurant.address

        Glide.with(holder.itemView.context)
            .load(restaurant.logo)
            .into(holder.logoImageView)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("restaurant_id", restaurant.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
