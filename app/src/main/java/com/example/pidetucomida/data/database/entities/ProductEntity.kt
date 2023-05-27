package com.example.pidetucomida.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pidetucomida.model.Product

@Entity
data class ProductEntity (val img: ByteArray,
                          @PrimaryKey(autoGenerate = false)
                          val idProducto: Int,
                          val nombre: String,
                          val precio: Double,
                          val tipo: String,
                          val descripcion: String)

fun Product.toDomain()= ProductEntity(img,idProducto,nombre,precio,tipo,descripcion)