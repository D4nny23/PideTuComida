package com.example.pidetucomida.data

import com.example.pidetucomida.api.ProductService
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.UtilsRetrofit

class RepositoryProduct {

    //Conecto con la API
    private val apiService = UtilsRetrofit().getRetrofit().create(ProductService::class.java)

    suspend fun getProductsByType(type: String): MutableList<ProductResponse> {
        return apiService.getProductsByType("resources/api/productos/$type")
    }

    suspend fun getProductsById(id: Int):ProductResponse {
        return apiService.getProductsById("resources/api/productos/id/$id")
    }
}