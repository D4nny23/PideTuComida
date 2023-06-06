package com.example.pidetucomida.api

import com.example.pidetucomida.model.Ingredient.IngredientResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface IngredientService {

    @GET
    suspend fun getIngredientsByIdProduct(@Url url:String):MutableList<IngredientResponse>

}