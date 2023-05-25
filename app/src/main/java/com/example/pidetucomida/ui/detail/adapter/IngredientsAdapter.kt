package com.example.pidetucomida.ui.detail.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pidetucomida.model.Ingredient.IngredientResponse

class IngredientsAdapter(
    private val modelList: MutableList<IngredientResponse>, private val context: Context
) : RecyclerView.Adapter<IngredientViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewModel =
        IngredientViewModel.from(parent)

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: IngredientViewModel, position: Int) {
        holder.bindView(modelList[position], context)
    }

}