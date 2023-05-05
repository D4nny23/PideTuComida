package com.example.pidetucomida.ui.login

import androidx.lifecycle.ViewModel
import com.example.pidetucomida.model.client.ClientDto

class MainActivityViewModel: ViewModel() {

    fun findClientByEmail(email:String): ClientDto{
        return ClientDto(1, "email@email.com", "pass1", "Name", "LastName", "Address", "666666666")
    }
}