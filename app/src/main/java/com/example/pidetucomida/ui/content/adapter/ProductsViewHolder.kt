package com.example.pidetucomida.ui.content.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.databinding.RowProductsBinding
import com.example.pidetucomida.model.product.ProductResponse

class ProductsViewHolder private constructor(
    private val binding: RowProductsBinding, var listener: OnClickListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bindView(model: ProductResponse, context: Context) {
        Glide.with(context)
            .asBitmap()
            .load(model.img)
            .apply(RequestOptions().override(300, 300))
            .centerCrop()
            .into(binding.imageView2)

        binding.tv1.text = model.nombre
        binding.tv2.text = "Precio: "+ model.precio.toString()+"€"

        binding.btnMoreInf.setOnClickListener {
            listener.onClick(model)
        }

    }

    companion object {
        fun from(parent: ViewGroup, listener: OnClickListener): ProductsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RowProductsBinding.inflate(inflater, parent, false)
            return ProductsViewHolder(binding, listener)
        }
    }


    interface OnClickListener {
        fun onClick(model: ProductResponse)
//        fun onUpdateItems(mutableList: MutableList<ProductResponse>)
    }
}