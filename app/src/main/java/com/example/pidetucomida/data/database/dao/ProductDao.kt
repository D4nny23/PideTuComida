package com.example.pidetucomida.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pidetucomida.data.database.entities.ProductEntity
import com.example.pidetucomida.model.Product

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
    suspend fun updateProduct(idProduct: Int): Int

    @Query("Update productentity set precioTotal= precio*cantidad where idProducto=:idProduct")
    suspend fun updateTotalPrice(idProduct: Int): Int

    @Query("Select SUM(precioTotal) from productentity")
    suspend fun getTotalPrice(): Double

    @Query("Update productentity set cantidad= cantidad-1 where idProducto = :id")
    suspend fun removeQuantity(id: Int)

    @Query("Select cantidad from productentity where idProducto = :id")
    suspend fun getQuantityById(id: Int):Int

    @Query("Delete from productentity where idProducto= :id")
    suspend fun deleteProduct(id: Int)

    @Query("UPDATE productentity SET precioTotal = ROUND(CAST(precioTotal - :precio AS DECIMAL(10, 2)), 2) WHERE idProducto = :id")
    suspend fun updateTotalPriceWhenRemoveProduct(precio: Double, id: Int)

    @Query("Delete from productentity")
    suspend fun deleteAll()
    @Query("UPDATE productentity SET precioTotal = ROUND(CAST(precioTotal + :precio AS DECIMAL(10, 2)), 2) WHERE idProducto = :id")
    suspend fun updateTotalPriceAddProduct(precio: Double, id: Int)
    @Query("Update productentity set cantidad= cantidad+1 where idProducto = :id")
    suspend fun addQuantityProduct(id: Int)

    @Query("Select * from productentity where idProducto= :id")
    suspend fun getProduct(id:Int): Product

}