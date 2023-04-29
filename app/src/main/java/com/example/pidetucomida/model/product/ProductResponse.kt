package com.example.pidetucomida.model.product

data class ProductResponse(
    val idProducto: Int,
    val nombre: String,
    val idIngrediente: Int,
    val precio: Double
)