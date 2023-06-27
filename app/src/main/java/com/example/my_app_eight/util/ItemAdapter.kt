package com.example.my_app_eight.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app_eight.R
import com.example.my_app_eight.models.ProductResponse
import kotlinx.android.synthetic.main.custom_item.view.*

class ItemAdapter(private val listener: RecyclerListener? = null, private val productList: MutableList<ProductResponse>) : RecyclerView.Adapter<ItemAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        holder.itemView.three_dots_btn.setOnClickListener {
            listener?.deleteShoe(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun addItem(product: ProductResponse) {
        productList.add(product)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name_of_item)
        private val priceTextView: TextView = itemView.findViewById(R.id.price_of_item)
        private val likedTextView: TextView = itemView.findViewById(R.id.amount_liked)

        fun bind(product: ProductResponse) {
            nameTextView.text = product.name
            priceTextView.text = product.price
            likedTextView.text = product.like_count
        }
    }
    fun setProducts(products: List<ProductResponse>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }
}
