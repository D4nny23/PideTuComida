package com.example.pidetucomida.ui.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.databinding.RowCartProductsBinding
import com.example.pidetucomida.databinding.RowProductsBinding
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.model.product.ProductResponse

class CartViewHolder private constructor(
    private val binding: RowCartProductsBinding
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

    }

    companion object {
        fun from(parent: ViewGroup): CartViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RowCartProductsBinding.inflate(inflater, parent, false)
            return CartViewHolder(binding)
        }
    }
}