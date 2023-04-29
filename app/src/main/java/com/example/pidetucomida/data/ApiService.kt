package com.example.pidetucomida.data

import com.example.pidetucomida.model.client.Client
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("")
    suspend fun createUser(@Body client: Client)
}