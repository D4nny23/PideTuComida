package com.example.pidetucomida.data

import com.example.pidetucomida.model.client.Client
import com.example.pidetucomida.model.client.UsersResponse
import com.example.pidetucomida.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST("")
    suspend fun createUser(@Body client: Client)

    @GET
    suspend fun getProducts(@Url url:String):List<UsersResponse>

}