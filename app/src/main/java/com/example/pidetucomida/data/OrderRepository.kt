package com.example.pidetucomida.data

import com.example.pidetucomida.api.OrderService
import com.example.pidetucomida.model.order.Order
import com.example.pidetucomida.model.order.OrderProducts
import com.example.pidetucomida.utils.UtilsRetrofit

class OrderRepository {

    private val apiService= UtilsRetrofit().getRetrofit().create(OrderService::class.java)

    suspend fun insertOrder(o:Order):Int{
        return apiService.createOrder(o)
    }

    suspend fun insertProductsInOrder(op:OrderProducts):Boolean{
        return apiService.insertProductsInOrder(op)
    }
}