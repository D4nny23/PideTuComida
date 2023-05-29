package com.example.pidetucomida.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pidetucomida.data.database.entities.ProductEntity
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.model.product.ProductResponse

@Dao
interface ProductDao {

    @Query("SELECT COUNT(*) FROM productentity")
    suspend fun getProductCount(): Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM productentity")
    suspend fun getProducts(): MutableList<Product>

    @Query("SELECT COUNT(*) FROM productentity WHERE idProducto = :productId")
    suspend fun getProductCountById(productId: Int): Int

    @Query("Update productentity set cantidad= cantidad + 1 where idProducto=:idProduct")
    suspend fun updateProduct(idProduct: Int):Int

    @Query("Update productentity set precioTotal= precio*cantidad where idProducto=:idProduct")
    suspend fun updateTotalPrice(idProduct: Int):Int

    @Query("Select SUM(precioTotal) from productentity")
    suspend fun getTotalPrice():Double
}