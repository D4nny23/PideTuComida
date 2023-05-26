package com.example.pidetucomida.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pidetucomida.databinding.RowIngredientBinding
import com.example.pidetucomida.model.Ingredient.IngredientResponse

class IngredientViewModel private constructor(
    private val binding: RowIngredientBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(model: IngredientResponse, context: Context) {
        binding.tvName.text= model.nombre
    }

    companion object {
        fun from(parent: ViewGroup): IngredientViewModel {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RowIngredientBinding.inflate(inflater, parent, false)
            return IngredientViewModel(binding)
        }
    }
}
