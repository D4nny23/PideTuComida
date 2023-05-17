package com.example.pidetucomida.data

import com.example.pidetucomida.api.ApiService
import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.model.client.ClientResponse
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.UtilsRetrofit
import retrofit2.Response

class Repository {

    //Conecto con la API
    private val apiService = UtilsRetrofit().getRetrofit().create(ApiService::class.java)

    suspend fun getProducts(): MutableList<ProductResponse> {
        return apiService.getProducts("resources/api/productos")
    }

    suspend fun addClient(client: ClientDto): Response<Boolean> {
        return apiService.createClient(client)
    }

    suspend fun login(email: String): ClientResponse {
        return apiService.getClient("resources/api/cliente/$email")
    }

    suspend fun getProductsByType(type: String): MutableList<ProductResponse> {
        return apiService.getProductsByType("resources/api/productos/$type")

    }
}