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

    suspend fun removeQuantity(id:Int){
        dao.removeQuantity(id)
    }

    suspend fun getQuantityById(id:Int) :Int{
       return dao.getQuantityById(id)
    }

    suspend fun deleteProduct(id: Int){
        dao.deleteProduct(id)
    }

    suspend fun updateTotalPriceWhenRemoveProduct(precio: Double, id: Int){
        dao.updateTotalPriceWhenRemoveProduct(precio,id)
    }
    suspend fun updateTotalPriceWhenAddProduct(precio: Double, id: Int){
        dao.updateTotalPriceAddProduct(precio,id)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

    suspend fun addQuantityProduct(idProducto: Int) {
    dao.addQuantityProduct(idProducto)
    }
}