package com.example.pidetucomida.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryUsers
import com.example.pidetucomida.model.client.ClientResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt

class MainActivityViewModel : ViewModel() {
    private val repository = RepositoryUsers();

    private val _client = MutableLiveData<ClientResponse?>()
    val client: LiveData<ClientResponse?> = _client
    fun findClientByEmail(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.login(email)
                val isCorrectPassword = BCrypt.checkpw(pass, response.pass)
                if(isCorrectPassword){
                    _client.postValue(response)
                }else{
                    _client.postValue(null)
                }
            } catch (e: Exception) {
                _client.postValue(null)
            }
        }
    }


}