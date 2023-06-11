package com.example.pidetucomida.data

import com.example.pidetucomida.R
import com.example.pidetucomida.api.IngredientService
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.utils.UtilsRetrofit
import com.example.pidetucomida.utils.Result
import java.lang.Exception

class RepositoryIngredient {
    private val apiService=UtilsRetrofit().getRetrofit().create(IngredientService::class.java)

    suspend fun getIngredientsByIdProduct(id: Int): Result<MutableList<IngredientResponse>> {
        return try {
            Result.Success(apiService.getIngredientsByIdProduct("resources/api/ingredientes/$id"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }

    }
}