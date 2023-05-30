package com.example.pidetucomida.model.client

data class ClientDto(
    val id: Int? = null,
    var correo: String,
    var pass: String,
    var nombre: String,
    var apellido: String,
    var direccionEnvio: String,
    var telefono: String
){
    constructor() : this(
        null,
        "",
        "",
        "",
        "",
        "",
        ""
    )
}

data class ClientResponse(val idCliente: Int,
                          var correo: String,
                          var pass: String,
                          var nombre: String,
                          var apellido: String,
                          var direccionEnvio: String,
                          var telefono: String)
