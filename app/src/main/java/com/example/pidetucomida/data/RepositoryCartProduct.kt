package com.example.pidetucomida.data

import com.example.pidetucomida.data.database.dao.ProductDao
import com.example.pidetucomida.data.database.entities.toDomain
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.model.toDomain

class RepositoryCartProduct (private val dao: ProductDao){
    suspend fun insertProduct(product: ProductResponse):Long{
        val product= product.toDomain()
        return dao.insertProduct(product.toDomain())
    }

    suspend fun returnCountProducts():Int{
        return dao.getProductCount()
    }
}