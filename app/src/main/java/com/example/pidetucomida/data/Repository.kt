package com.example.pidetucomida.data

import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.model.client.ClientResponse
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.UtilsRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
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

    suspend fun login(email: String): ClientResponse{
        return apiService.getClient("resources/api/cliente/$email")
    }
}