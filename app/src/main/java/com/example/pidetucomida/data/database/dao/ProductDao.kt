package com.example.pidetucomida.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.pidetucomida.data.database.entities.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: ProductEntity)
}