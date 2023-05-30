package com.example.pidetucomida.ui.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.databinding.RowCartProductsBinding
import com.example.pidetucomida.model.Product

class CartViewHolder private constructor(
    private val binding: RowCartProductsBinding, var listener: OnClickListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bindView(model: Product, context: Context) {
        Glide.with(context)
            .asBitmap()
            .load(model.img)
            .apply(RequestOptions().override(300, 300))
            .centerCrop()
            .into(binding.imageView2)

        binding.tvName.text = model.nombre
        binding.tvPrice.text = "Precio: "+ model.precio.toString()+"€"
        binding.tvQuantity.text= "Cantidad: "+ model.cantidad.toString()
        binding.ivRemove.setOnClickListener{
            listener.onClickRemove(model)
        }

    }

    companion object {
        fun from(parent: ViewGroup, listener: OnClickListener): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RowCartProductsBinding.inflate(inflater, parent, false)
            return CartViewHolder(binding, listener)
        }
    }

    interface OnClickListener {
        fun onClickRemove(product:Product)
//        fun onUpdateItems(mutableList: MutableList<ProductResponse>)
    }
}