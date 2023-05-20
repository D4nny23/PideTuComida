package com.example.pidetucomida.ui.login

import android.content.Context
import android.widget.Toast
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

    private val _client =MutableLiveData<Boolean>()
    val client: LiveData<Boolean> = _client
    fun findClientByEmail(
        context: Context,
        email: String,
        pass: String,
        callback: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.login(email)
                    val isCorrectPassword = BCrypt.checkpw(pass, response.pass)
                    callback(isCorrectPassword)
                } catch (e: Exception) {
                    Toast.makeText(context, "Hubo un error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}