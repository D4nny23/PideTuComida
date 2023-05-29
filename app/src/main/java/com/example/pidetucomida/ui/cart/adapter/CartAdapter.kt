package com.example.pidetucomida.ui.cart.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.model.product.ProductResponse

class CartAdapter(
    private val modelList: MutableList<Product>, private val context: Context
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder.from(parent)

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindView(modelList[position], context)
    }

    fun addAll(results: MutableList<Product>) {
        //listener.onUpdateItems(results)
        modelList.clear()
        modelList.addAll(results)
        notifyDataSetChanged()
    }

}