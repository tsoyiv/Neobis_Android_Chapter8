package com.example.my_app_eight.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app_eight.R
import com.example.my_app_eight.models.ProductResponse
import kotlinx.android.synthetic.main.custom_item.view.*

class ItemAdapter(
    private val listener: RecyclerListener? = null,
    private val productList: MutableList<ProductResponse>,
) : RecyclerView.Adapter<ItemAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        holder.itemView.three_dots_btn.setOnClickListener {
            listener?.deleteProduct(product.id)
            listener?.updateProduct(product.id, product)
        }
        holder.itemView.liked_btn.setOnClickListener {
            listener?.likeProduct(product.id)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(product)
        }
    }

    private var onItemClickListener: ((ProductResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (ProductResponse) -> Unit) {
        onItemClickListener = listener
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

    fun updateProducts(products: List<ProductResponse>) {
        val diffCallback = ProductDiffCallback(productList, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        productList.clear()
        productList.addAll(products)
        diffResult.dispatchUpdatesTo(this)
    }


    fun setProducts(products: List<ProductResponse>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    class ProductDiffCallback(
        private val oldList: List<ProductResponse>,
        private val newList: List<ProductResponse>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList[oldItemPosition]
            val newProduct = newList[newItemPosition]
            return oldProduct.id == newProduct.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldProduct = oldList[oldItemPosition]
            val newProduct = newList[newItemPosition]
            return oldProduct == newProduct
        }
    }

    fun deleteItem(position: Int) {
        if (position >= 0 && position < productList.size) {
            productList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, productList.size)
        }
    }
}
