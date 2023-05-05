package com.example.pidetucomida.model.product

data class ProductResponse(
    val idIngrediente: Int,
    val idProducto: Int,
    val nombre: String,
    val precio: Double
)