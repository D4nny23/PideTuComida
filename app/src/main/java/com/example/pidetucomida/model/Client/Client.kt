package com.example.pidetucomida.model.Client

data class Client(
    val id: Int,
    val email: String,
    val pass: String,
    val name: String,
    val lastName: String,
    val address: String,
    val numberPhone: String
)