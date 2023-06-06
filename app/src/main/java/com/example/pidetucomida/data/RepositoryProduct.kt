package com.example.pidetucomida.data

import com.example.pidetucomida.R
import com.example.pidetucomida.api.ProductService
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.UtilsRetrofit
import com.example.pidetucomida.utils.Result
import java.lang.Exception

class RepositoryProduct {

    //Conecto con la API
    private val apiService = UtilsRetrofit().getRetrofit().create(ProductService::class.java)

    suspend fun getProductsByType(type: String): Result<MutableList<ProductResponse>> {
        return try {
            Result.Success(apiService.getProductsByType("resources/api/productos/$type"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }

    suspend fun getProductsById(id: Int): Result<ProductResponse> {
        return try {
            Result.Success(apiService.getProductsById("resources/api/productos/id/$id"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }


}