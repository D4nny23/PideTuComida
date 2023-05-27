package com.example.pidetucomida.data

import com.example.pidetucomida.data.database.dao.ProductDao
import com.example.pidetucomida.data.database.entities.toDomain
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.model.toDomain

class RepositoryCartProduct (private val dao: ProductDao){
    suspend fun insertProduct(product: ProductResponse){
        val product= product.toDomain()
        dao.insertProduct(product.toDomain())
    }
}