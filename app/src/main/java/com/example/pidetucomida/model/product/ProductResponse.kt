package com.example.pidetucomida.model.product

data class ProductResponse(
    val img: ByteArray,
    val idIngrediente: Int,
    val idProducto: Int,
    val nombre: String,
    val precio: Double,
    val tipo: String
)