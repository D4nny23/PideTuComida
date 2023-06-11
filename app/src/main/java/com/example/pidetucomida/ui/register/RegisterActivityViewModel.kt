package com.example.pidetucomida.ui.register

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.R
import com.example.pidetucomida.data.RepositoryUsers
import com.example.pidetucomida.model.client.ClientDto
import kotlinx.coroutines.launch
import com.example.pidetucomida.utils.Result

class RegisterActivityViewModel : ViewModel() {

    private val _isValidEmail = MutableLiveData<Int>()
    val isValidEmail: LiveData<Int> = _isValidEmail

    private val _isValidPhone = MutableLiveData<Int>()
    val isValidPhone: LiveData<Int> = _isValidPhone

    private val _isValidPass = MutableLiveData<Int>()
    val isValidPass: LiveData<Int> = _isValidPass

    private val _isValidName = MutableLiveData<Int>()
    val isValidName: LiveData<Int> = _isValidName

    private val _isValidRepPass = MutableLiveData<Int>()
    val isValidRepPass: LiveData<Int> = _isValidRepPass

    private val _isValidLastName = MutableLiveData<Int>()
    val isValidLastName: LiveData<Int> = _isValidLastName

    private val _isValidAddress = MutableLiveData<Int>()
    val isValidAddress: LiveData<Int> = _isValidAddress

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _setError = MutableLiveData<Int>()
    val setError: LiveData<Int> = _setError

    private val repository = RepositoryUsers()
    fun setupEmail(email: String) {
        viewModelScope.launch {
            if (email.isNotEmpty()) {
                when (val response = repository.existEmail(email)) {
                    is Result.Success -> {
                        if (response.data) {
                            _isValidEmail.postValue(R.string.email_already_exist)
                        } else {
                            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                _isValidEmail.postValue(R.string.email_ok)
                            } else {
                                _isValidEmail.postValue(R.string.email_invalid)
                            }
                        }
                    }

                    is Result.Error -> {
                        _setError.postValue(response.message)
                    }
                }
            } else {
                _isValidEmail.postValue(R.string.obligatory_field)
            }
        }
    }

    fun setupPassword(pass: String) {
        val containsLetterUpperCase = pass.matches(".*[A-Z].*".toRegex())
        val containsNumbers = pass.matches(".*[0-9].*".toRegex())
        val containsSpecialChars = pass.matches(".*[^A-Za-z0-9].*".toRegex())

        if (pass.isEmpty()) {
            _isValidPass.value = R.string.obligatory_field
        } else if (!containsLetterUpperCase || !containsNumbers || !containsSpecialChars) {
            _isValidPass.value = R.string.containts_numbers_caps_specialcharacters
        } else {
            _isValidPass.value = R.string.pass_ok
        }
    }

    fun setupRepeatPassword(pass: String) {
        if (pass.isEmpty()) {
            _isValidRepPass.value = R.string.obligatory_field
        } else {
            _isValidRepPass.value = R.string.pass_ok
        }
    }

    fun setupName(name: String) {
        val containsSpecialChars = name.matches(".*[^\\p{L}].*".toRegex(RegexOption.IGNORE_CASE))
        if (name.isEmpty()) {
            _isValidName.value = R.string.obligatory_field
        } else if (containsSpecialChars) {
            _isValidName.value = R.string.name_no_contains_special_characters
        } else {
            _isValidName.value = R.string.name_ok
        }
    }

    fun setupLastName(lastName: String) {
        val containsSpecialChars =
            lastName.matches(".*[^\\p{L}].*".toRegex(RegexOption.IGNORE_CASE))
        if (lastName.isEmpty()) {
            _isValidLastName.value = R.string.obligatory_field
        } else if (containsSpecialChars) {
            _isValidLastName.value = R.string.lastname_no_contains_special_characters
        } else {
            _isValidLastName.value = R.string.lastname_ok

        }
    }

    fun setupAdress(address: String) {
        val containsSpecialChars = address.matches(".*[^A-Za-z0-9áéíóúÁÉÍÓÚüÜ ,].*".toRegex())
        if (address.isEmpty()) {
            _isValidAddress.value = R.string.obligatory_field
        } else if (containsSpecialChars) {
            _isValidAddress.value = R.string.address_no_contains_special_characters
        } else {
            _isValidAddress.value = R.string.address_ok
        }
    }


    fun setupNumberPhone(number: String): Boolean {
        viewModelScope.launch {
            if (number.isNotEmpty()) {
                when (val response = repository.existNumber(number)) {
                    is Result.Success -> {
                        if (response.data) {
                            _isValidPhone.postValue(R.string.phone_already_exist)
                        } else {
                            if (number.length == 9) {
                                _isValidPhone.postValue(R.string.phone_ok)
                            } else {
                                _isValidPhone.postValue(R.string.invalid_phone)
                            }
                        }
                    }

                    is Result.Error -> {
                        _setError.postValue(response.message)
                    }
                }
            } else {
                _isValidPhone.postValue(R.string.obligatory_field)
            }
        }
        return false
    }


    fun addClient(
        client: ClientDto,
        context: Context
    ) {//Este metodo añadirá el cliente a la base de datos
        viewModelScope.launch {
            when (val response = repository.addClient(client)) {
                is Result.Success -> {
                    if (response.data.isSuccessful) {
                        _success.postValue(true)
                    } else {
                        _success.postValue(false)

                    }
                }

                is Result.Error -> {
                    _setError.postValue(response.message)
                }
            }


        }
    }
}