package com.example.pidetucomida.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryUsers
import com.example.pidetucomida.model.client.ClientResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import com.example.pidetucomida.utils.Result

class MainActivityViewModel : ViewModel() {
    private val repository = RepositoryUsers();

    private val _client = MutableLiveData<ClientResponse?>()
    val client: LiveData<ClientResponse?> = _client

    private val _setError = MutableLiveData<Int>()
    val setError: LiveData<Int?> = _setError
    fun findClientByEmail(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
                when(val response = repository.login(email)){
                is Result.Success ->{
                    val responseData= response.data
                    val isCorrectPassword = BCrypt.checkpw(pass, response.data.pass)
                    if(isCorrectPassword){
                        _client.postValue(responseData)
                    }else{
                        _client.postValue(null)
                    }
                }
                is Result.Error ->{
                    _setError.postValue(response.message)
                }
            }


        }
    }


}