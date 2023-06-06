package com.example.pidetucomida.data

import com.example.pidetucomida.api.ApiService
import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.model.client.ClientResponse
import com.example.pidetucomida.utils.UtilsRetrofit
import retrofit2.Response

class RepositoryUsers {

    //Conecto con la API
    private val apiService = UtilsRetrofit().getRetrofit().create(ApiService::class.java)

    suspend fun addClient(client: ClientDto): Response<Boolean> {
        return apiService.createClient(client)
    }

    suspend fun login(email: String): ClientResponse {
        return apiService.getClient("resources/api/cliente/$email")
    }
}