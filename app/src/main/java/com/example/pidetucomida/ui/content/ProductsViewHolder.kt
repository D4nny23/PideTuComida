package com.example.pidetucomida.ui.content

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pidetucomida.databinding.RowProductsBinding
import com.example.pidetucomida.model.product.ProductResponse

class ProductsViewHolder private constructor(
    private val binding: RowProductsBinding,
    var listener: OnClickListener
): RecyclerView.ViewHolder(binding.root) {


    fun bindView(model: ProductResponse, context: Context) {
        binding.tv1.text = model.nombre
        binding.tv2.text = model.idProducto.toString()
        binding.tv3.text = model.idIngrediente.toString()
        binding.tv4.text = model.precio.toString()

    }

    companion object {
        fun from(parent: ViewGroup, listener: OnClickListener): ProductsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RowProductsBinding.inflate(inflater, parent, false)
            return ProductsViewHolder(binding, listener)
        }
    }


    interface OnClickListener {
//        fun onUpdateItems(mutableList: MutableList<ProductResponse>)
    }
}