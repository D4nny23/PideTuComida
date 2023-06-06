package com.example.pidetucomida.api

import com.example.pidetucomida.model.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductService {
    @GET
    suspend fun getProductsByType(@Url url:String):MutableList<ProductResponse>

    @GET
    suspend fun getProductsById(@Url url:String):ProductResponse
}