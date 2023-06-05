package com.example.pidetucomida.ui.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.RowCartProductsBinding
import com.example.pidetucomida.model.Product

class CartViewHolder private constructor(
    private val binding: RowCartProductsBinding, var listener: OnClickListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bindView(model: Product, context: Context, position: Int) {
        Glide.with(context)
            .asBitmap()
            .load(model.img)
            .apply(RequestOptions().override(300, 300))
            .centerCrop()
            .into(binding.imageView2)

        binding.tvName.text = model.nombre
        binding.tvPrice.text = context.getString(R.string.price)+ model.precio.toString()+context.getString(R.string.euro)
        binding.tvQuantity.text= context.getString(R.string.quantity)+ model.cantidad.toString()
        binding.ibRemove.setOnClickListener{
            listener.onClickRemove(model)
        }

        binding.ibAdd.setOnClickListener{
            listener.onClickAdd(model,position)
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
        fun onClickAdd(product: Product, position:Int)
//        fun onUpdateItems(mutableList: MutableList<ProductResponse>)
    }
}