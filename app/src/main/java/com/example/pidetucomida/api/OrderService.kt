package com.example.pidetucomida.api

import com.example.pidetucomida.model.order.Order
import com.example.pidetucomida.model.order.OrderProducts
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {
    @POST("resources/api/pedido/insert")
    suspend fun createOrder(@Body o:Order):Int

    @POST("resources/api/productos_pedido/insert")
    suspend fun insertProductsInOrder(@Body op:OrderProducts):Boolean
}