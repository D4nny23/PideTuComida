package com.example.pidetucomida.data

import com.example.pidetucomida.R
import com.example.pidetucomida.api.OrderService
import com.example.pidetucomida.model.order.Order
import com.example.pidetucomida.model.order.OrderProducts
import com.example.pidetucomida.utils.Result
import com.example.pidetucomida.utils.UtilsRetrofit
import java.lang.Exception

class OrderRepository {

    private val apiService= UtilsRetrofit().getRetrofit().create(OrderService::class.java)

    suspend fun insertOrder(o:Order):Result<Int>{
        return try {
            Result.Success(apiService.createOrder(o))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }

    suspend fun insertProductsInOrder(op:OrderProducts):Boolean{
        return apiService.insertProductsInOrder(op)
    }
}