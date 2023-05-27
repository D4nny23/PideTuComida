package com.example.pidetucomida.model

import com.example.pidetucomida.model.product.ProductResponse


data class Product (val img: ByteArray,
                    val idProducto: Int,
                    val nombre: String,
                    val precio: Double,
                    val tipo: String,
                    val descripcion: String)

fun ProductResponse.toDomain()= Product(img,idProducto,nombre,precio,tipo,descripcion)