package com.example.pidetucomida.model.order

data class Order(
    private val idCliente: Int,
    private val comentario: String,
    private val formaDePago: String
)
