package com.example.my_app_eight.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app_eight.R
import com.example.my_app_eight.fragments.usage_fragments.item_setting_fragments.UpdateFragment
import com.example.my_app_eight.fragments.usage_fragments.profile_fragments.user_menu_fragments.UserItemsFragmentDirections
import com.example.my_app_eight.models.ProductResponse
import kotlinx.android.synthetic.main.bottom_dialog.view.*
import kotlinx.android.synthetic.main.custom_item.view.*
import kotlinx.android.synthetic.main.fragment_update.view.*

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
            //listener?.updateProduct(product.id, product)
        }
        holder.itemView.liked_btn.setOnClickListener {
            listener?.likeProduct(product.id)
        }
//        holder.itemView.liked_btn.setOnClickListener {
//            listener?.unLike(product.id)
//        }
//        Holder.access_token.apply {
//            setOnItemClickListener {
//                onItemClickListener?.let { it(product) }
//            }
//        }
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

    fun setProducts(products: List<ProductResponse>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        if (position >= 0 && position < productList.size) {
            productList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, productList.size)
        }
    }
}
