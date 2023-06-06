package com.example.pidetucomida.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pidetucomida.data.database.dao.ProductDao
import com.example.pidetucomida.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract val dao:ProductDao
}