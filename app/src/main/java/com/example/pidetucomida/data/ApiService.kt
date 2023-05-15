package com.example.pidetucomida.data

import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.model.client.ClientResponse
import com.example.pidetucomida.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST("resources/api/insert")
    suspend fun createClient(@Body client: ClientDto): Response<Boolean>


    @GET
    suspend fun getClient(@Url url:String):ClientResponse

    @GET
    suspend fun getProducts(@Url url:String):MutableList<ProductResponse>

    @GET
    suspend fun getProductsByType(@Url url:String):MutableList<ProductResponse>

}