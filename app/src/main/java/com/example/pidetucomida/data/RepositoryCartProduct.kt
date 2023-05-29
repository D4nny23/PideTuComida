package com.example.pidetucomida.data

import com.example.pidetucomida.data.database.dao.ProductDao
import com.example.pidetucomida.data.database.entities.toDomain
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.model.toDomain

class RepositoryCartProduct (private val dao: ProductDao){
    suspend fun insertProduct(product: ProductResponse){
        val product= product.toDomain()
        dao.insertProduct(product.toDomain())
    }

    suspend fun returnCountProducts():Int{
        return dao.getProductCount()
    }

    suspend fun getProducts(): MutableList<Product>{
        return dao.getProducts()
    }

    suspend fun getProductById(idProduct:Int): Int {
        return dao.getProductCountById(idProduct)
    }

    suspend fun updateQuantity(idProduct: Int):Int{
        return dao.updateProduct(idProduct)
    }

    suspend fun updateTotalPrice(idProduct: Int){
        dao.updateTotalPrice(idProduct)
    }

    suspend fun getTotalPrice(): Double{
        return dao.getTotalPrice()
    }

}