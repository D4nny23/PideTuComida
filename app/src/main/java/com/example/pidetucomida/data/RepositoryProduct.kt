package com.example.pidetucomida.data

import android.content.Context
import android.widget.Toast
import com.example.pidetucomida.R
import com.example.pidetucomida.api.ProductService
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.UtilsRetrofit
import com.example.pidetucomida.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryProduct {

    //Conecto con la API
    private val apiService = UtilsRetrofit().getRetrofit().create(ProductService::class.java)

    suspend fun getProductsByType(type: String): Result<MutableList<ProductResponse>> {
        try {
            return Result.Success(apiService.getProductsByType("resources/api/productos/$type"))
        } catch (e: java.net.SocketTimeoutException) {
            return Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            return Result.Error(R.string.connect_exception)
        }
    }

    suspend fun getProductsById(id: Int): ProductResponse {
        return apiService.getProductsById("resources/api/productos/id/$id")
    }

    private suspend fun showToast(context: Context, message: String) {
        // Asegúrate de llamar esto en el hilo principal de la interfaz de usuario
        // (por ejemplo, desde un Activity o Fragment)
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}