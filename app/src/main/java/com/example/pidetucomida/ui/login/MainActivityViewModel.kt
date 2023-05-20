package com.example.pidetucomida.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt

class MainActivityViewModel : ViewModel() {
    private val repository = RepositoryUsers();
    fun findClientByEmail(email: String, pass: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val response = repository.login(email)

                    response.let {
                        result.value = BCrypt.checkpw(pass, response.pass)
                    }
                } catch (e: Exception) {
                    result.value = false
                }
            }


            }
        return result
    }

}