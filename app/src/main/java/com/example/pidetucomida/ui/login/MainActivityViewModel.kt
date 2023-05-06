package com.example.pidetucomida.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.Repository
import com.example.pidetucomida.databinding.ActivityMainBinding
import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.model.client.ClientResponse
import com.example.pidetucomida.utils.UtilsChyper
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class MainActivityViewModel : ViewModel() {
    private val repository = Repository();
    fun findClientByEmail(email: String, pass: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            try {
                val response = repository.login(email)

                response.let {
                    result.value = BCrypt.checkpw(pass, response.pass)
                }
            } catch (e: Exception) {
                result.value = false
            }
        }

        return result
    }

}