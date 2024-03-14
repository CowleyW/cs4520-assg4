package com.cs4520.assignment4

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter (private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(productView : View) : RecyclerView.ViewHolder(productView) {
        val productRow: LinearLayout = productView.findViewById(R.id.product_row)
        val productImage: ImageView = productView.findViewById(R.id.product_image)
        val productName: TextView = productView.findViewById(R.id.product_name)
        val productExpiration: TextView = productView.findViewById(R.id.product_expiration)
        val productPrice: TextView = productView.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflator = LayoutInflater.from(context)
        val productView = inflator.inflate(R.layout.item_product, parent, false)

        return ViewHolder(productView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        if (product.type == "Food") {
            holder.productRow.setBackgroundColor(Color.parseColor("#FFD965"))
            holder.productImage.setImageResource(R.drawable.food_cs4520)
        } else {
            holder.productRow.setBackgroundColor(Color.parseColor("#E06666"))
            holder.productImage.setImageResource(R.drawable.equipment_cs4520)
        }

        holder.productName.text = product.name
        if (product.expirationDate == null) {
            holder.productExpiration.visibility = View.GONE
        } else {
            holder.productExpiration.text = product.expirationDate
        }
        holder.productPrice.text = "$ " + product.price.toString()

    }
}