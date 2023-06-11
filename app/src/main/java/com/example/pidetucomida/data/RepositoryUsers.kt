package com.example.pidetucomida.data

import com.example.pidetucomida.R
import com.example.pidetucomida.api.ApiService
import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.model.client.ClientResponse
import com.example.pidetucomida.utils.Result
import com.example.pidetucomida.utils.UtilsRetrofit
import retrofit2.Response
import java.lang.Exception

class RepositoryUsers {

    //Conecto con la API
    private val apiService = UtilsRetrofit().getRetrofit().create(ApiService::class.java)

    suspend fun addClient(client: ClientDto): Result<Response<Boolean>> {
        return try {
            Result.Success( apiService.createClient(client))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }

    suspend fun login(email: String): Result<ClientResponse> {
        return try {
            Result.Success(apiService.login("resources/api/cliente/$email"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }

    suspend fun existEmail(email: String):Result<Boolean> {
        return try {
            Result.Success(apiService.getClient("resources/api/cliente/existeEmail/$email"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }
//

    suspend fun existNumber(number: String): Result<Boolean> {
        return try {
            Result.Success(apiService.getClient("resources/api/cliente/existeNumero/$number"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(R.string.time_out_exception)
        } catch (e: java.net.ConnectException) {
            Result.Error(R.string.connect_exception)
        } catch (e: Exception) {
            Result.Error(R.string.generic_error)
        }
    }
}