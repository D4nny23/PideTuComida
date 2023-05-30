package com.example.pidetucomida.model

import com.example.pidetucomida.model.product.ProductResponse


data class Product(
    var id: Int,
    val img: ByteArray,
    val idProducto: Int,
    val nombre: String,
    val precio: Double,
    val tipo: String,
    val descripcion: String,
    val cantidad: Int,
    val precioTotal: Double
)

fun ProductResponse.toDomain() = Product(0, img, idProducto, nombre, precio, tipo, descripcion, 0, 0.0)