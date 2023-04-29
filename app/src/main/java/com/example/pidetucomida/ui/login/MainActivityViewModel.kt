package com.example.pidetucomida.ui.login

import androidx.lifecycle.ViewModel
import com.example.pidetucomida.model.client.Client

class MainActivityViewModel: ViewModel() {

    fun findClientByEmail(email:String): Client{
        return Client(1, "email@email.com", "pass1", "Name", "LastName", "Address", "666666666")
    }
}