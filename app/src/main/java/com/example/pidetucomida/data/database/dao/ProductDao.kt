package com.example.pidetucomida.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pidetucomida.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT COUNT(*) FROM productentity")
    suspend fun getProductCount(): Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: ProductEntity): Long
}