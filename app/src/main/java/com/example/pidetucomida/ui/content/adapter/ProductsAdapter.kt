package com.example.pidetucomida.ui.content.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pidetucomida.model.product.ProductResponse

class ProductsAdapter(
    private val modelList: MutableList<ProductResponse>,
    private val context: Context,
    private val listener: ProductsViewHolder.OnClickListener
) : RecyclerView.Adapter<ProductsViewHolder>() {

    private var filteredList: MutableList<ProductResponse> = mutableListOf()

    init {
        filteredList.addAll(modelList)
        //listener.onUpdateItems(filteredList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder.from(parent, listener)

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindView(filteredList[position], context)
    }

    override fun getItemCount(): Int = filteredList.size

    fun addAll(results: MutableList<ProductResponse>) {
        //listener.onUpdateItems(results)
        filteredList.clear()
        filteredList.addAll(results)
        notifyDataSetChanged()
    }
}