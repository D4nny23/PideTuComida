package com.example.pidetucomida.model.order

data class OrderProducts(
    private val idPedido: Int,
    private val idProducto: Int,
    private val cantidad: Int
)