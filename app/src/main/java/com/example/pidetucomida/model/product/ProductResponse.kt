package com.example.pidetucomida.model.product

data class ProductResponse(
    val img: ByteArray,
    val idProducto: Int,
    val nombre: String,
    val precio: Double,
    val tipo: String,
    val descripcion: String
)