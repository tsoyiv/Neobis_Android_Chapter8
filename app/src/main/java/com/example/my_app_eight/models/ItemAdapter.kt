package com.example.my_app_eight.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app_eight.R

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    private var itemList = emptyList<NullPointerException>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
//        holder.itemView.name_of_item.text = currentItem.name
//        holder.itemView.price_of_item.text = currentItem.price
//        holder.itemView.amount_liked.text = currentItem.distributor
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}