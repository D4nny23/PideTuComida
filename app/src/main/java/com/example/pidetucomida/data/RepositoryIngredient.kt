package com.example.pidetucomida.data

import com.example.pidetucomida.api.IngredientService
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.utils.UtilsRetrofit

class RepositoryIngredient {
    private val apiService=UtilsRetrofit().getRetrofit().create(IngredientService::class.java)

    suspend fun getIngredientsByIdProduct(id: Int): MutableList<IngredientResponse> {
        return apiService.getIngredientsByIdProduct("resources/api/ingredientes/$id")
    }
}