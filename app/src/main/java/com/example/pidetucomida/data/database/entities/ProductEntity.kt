package com.example.pidetucomida.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pidetucomida.model.Product

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val img: ByteArray,
    val idProducto: Int,
    val nombre: String,
    val precio: Double,
    val tipo: String,
    val descripcion: String
)

fun Product.toDomain() = ProductEntity(id,img, idProducto, nombre, precio, tipo, descripcion)