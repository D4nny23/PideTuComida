package com.example.pidetucomida.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.R
import com.example.pidetucomida.data.RepositoryUsers
import com.example.pidetucomida.databinding.ActivityRegisterBinding
import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.utils.UtilsChyper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterActivityViewModel : ViewModel() {
    private var client: ClientDto= ClientDto()
    private val repository = RepositoryUsers()
    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupEmail(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        if (binding.etEmail.text.toString().isEmpty()) {
            setError(binding.tiEmail, context,context.getString(R.string.obligatory_field),background,binding.etEmail)
        } else if (!binding.etEmail.text.toString().contains('@')) {
            setError(binding.tiEmail,context,context.getString(R.string.containts_at),background,binding.etEmail)
        } else {
            removeError(binding.tiEmail,context,binding.etEmail)
            client.correo=binding.etEmail.text.toString()
            return true
        }
        return false
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupPassword(context: Context, binding: ActivityRegisterBinding, background: Int): Boolean {
        var response=false
        val containsLetterUpperCase = binding.etPassword.text?.matches(".*[A-Z].*".toRegex())
        val containsNumbers = binding.etPassword.text?.matches(".*[0-9].*".toRegex())
        val containsSpecialChars = binding.etPassword.text?.matches(".*[^A-Za-z0-9].*".toRegex())

        if (binding.etPassword.text.toString().isEmpty()) {
            setError(binding.tiPass, context ,context.getString(R.string.obligatory_field), background, binding.etPassword)
        } else if (containsLetterUpperCase == false || containsNumbers == false || containsSpecialChars == false) {
            setError(binding.tiPass, context ,context.getString(R.string.containts_numbers_caps_specialcharacters), background, binding.etPassword)
        } else {
            removeError(binding.tiPass, context,binding.etPassword)
            response=true
        }
        setupRepeatPassword(context,binding,background)
        response = response && setupRepeatPassword(context,binding,background)
        if (response) client.pass= UtilsChyper().hashPassword(binding.etPassword.text.toString())
        return response

    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupRepeatPassword(context: Context, binding: ActivityRegisterBinding, background: Int): Boolean {
        var response=false
        if (binding.etRepeatPass.text.toString().isEmpty()) {
            setError(binding.tiRepeatPass, context ,context.getString(R.string.obligatory_field), background, binding.etRepeatPass)
        } else if (binding.etRepeatPass.text.toString() != binding.etPassword.text.toString()) {
            setError(binding.tiRepeatPass, context ,context.getString(R.string.same_password), background, binding.etRepeatPass)
        } else {
            removeError(binding.tiRepeatPass, context,binding.etRepeatPass)
            response= true
        }
        return response
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupName(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        val containsSpecialChars = binding.etName.text?.matches(".*[^A-Za-z0-9].*".toRegex())
        if (binding.etName.text.toString().isEmpty()) {
            setError(binding.tiName, context ,context.getString(R.string.obligatory_field), background, binding.etName)
        } else if (containsSpecialChars==true) {
            setError(binding.tiName, context ,context.getString(R.string.name_no_contains_special_characters), background, binding.etName)
        } else {
            removeError(binding.tiName, context,binding.etName)
            client.nombre= binding.etName.text.toString()
            return true
        }
        return false
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupLastName(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        val containsSpecialChars = binding.etLastName.text?.matches(".*[^A-Za-z0-9].*".toRegex())
        if (binding.etLastName.text.toString().isEmpty()) {
            setError(binding.tiLastName, context ,context.getString(R.string.obligatory_field), background, binding.etLastName)
        } else if (containsSpecialChars==true) {
            setError(binding.tiLastName, context ,context.getString(R.string.lastname_no_contains_special_characters), background, binding.etLastName)
        } else {
            removeError(binding.tiLastName, context,binding.etLastName)
            client.apellido= binding.etLastName.text.toString()
            return true
        }
        return false
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupAdress(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        val containsSpecialChars = binding.etAdress.text?.matches(".*[^A-Za-z0-9áéíóúÁÉÍÓÚüÜ ,].*".toRegex())
        if (binding.etAdress.text.toString().isEmpty()) {
            setError(binding.tiAdress, context ,context.getString(R.string.obligatory_field), background, binding.etAdress)
        } else if (containsSpecialChars==true) {
            setError(binding.tiAdress, context ,context.getString(R.string.address_no_contains_special_characters), background, binding.etAdress)
        } else {
            removeError(binding.tiAdress, context,binding.etAdress)
            client.direccionEnvio= binding.etAdress.text.toString()
            return true
        }
        return false
    }


    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupNumberPhone(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        if (binding.etNumberPhone.text.toString().isEmpty()) {
            setError(binding.tiNumberPhone, context ,context.getString(R.string.obligatory_field), background, binding.etNumberPhone)
        } else if (binding.etNumberPhone.text.toString().length!=9){
            setError(binding.tiNumberPhone, context ,context.getString(R.string.number_phone_contains_9characters), background, binding.etNumberPhone)
        }else{
            removeError(binding.tiAdress, context,binding.etAdress)
            client.telefono= binding.etNumberPhone.text.toString()
            return true
        }
        return false
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun setError(ti: TextInputLayout, context:Context, message: String, background: Int, et:TextInputEditText){
        ti.error = message
        ti.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
        ti.background =
            ContextCompat.getDrawable(context, R.drawable.errorborder)
        et.compoundDrawableTintList = ColorStateList.valueOf(background)
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun removeError(ti: TextInputLayout, context:Context, et:TextInputEditText){
        ti.isErrorEnabled=false
        ti.errorIconDrawable=null
        ti.background =
            ContextCompat.getDrawable(context, R.drawable.border)
        et.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
    }


    fun addClient(context: Context){//Este metodo añadirá el cliente a la base de datos
        viewModelScope.launch {
            try {
                val response = repository.addClient(client)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Cliente añadido exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Hubo un error al añadir el cliente", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Hubo un error al añadir el cliente por parte del servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}