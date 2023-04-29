package com.example.pidetucomida.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivityViewModel : ViewModel() {

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupEmail(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        if (binding.etEmail.text.toString().isEmpty()) {
            setError(binding.tiEmail, context,"Campo obligatorio",background,binding.etEmail)
        } else if (!binding.etEmail.text.toString().contains('@')) {
            setError(binding.tiEmail,context,"Este campo debe contener '@'",background,binding.etEmail)
        } else {
            removeError(binding.tiEmail,context,binding.etEmail)
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
            setError(binding.tiPass, context ,"Campo obligatorio", background, binding.etPassword)
        } else if (containsLetterUpperCase == false || containsNumbers == false || containsSpecialChars == false) {
            setError(binding.tiPass, context ,"La contraseña debe contener numeros, caracteres especiales y mayúsculas", background, binding.etPassword)
        } else {
            removeError(binding.tiPass, context,binding.etPassword)
            response=true
        }
        setupRepeatPassword(context,binding,background)
        response = response && setupRepeatPassword(context,binding,background)
        return response
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupRepeatPassword(context: Context, binding: ActivityRegisterBinding, background: Int): Boolean {
        var response=false
        if (binding.etRepeatPass.text.toString().isEmpty()) {
            setError(binding.tiRepeatPass, context ,"Campo obligatorio", background, binding.etRepeatPass)
        } else if (binding.etRepeatPass.text.toString() != binding.etPassword.text.toString()) {
            setError(binding.tiRepeatPass, context ,"Las contraseñas deben ser iguales", background, binding.etRepeatPass)
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
            setError(binding.tiName, context ,"Campo obligatorio", background, binding.etName)
        } else if (containsSpecialChars==true) {
            setError(binding.tiName, context ,"El nombre no puede contener caracteres especiales", background, binding.etName)
        } else {
            removeError(binding.tiName, context,binding.etName)
            return true
        }
        return false
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupLastName(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        val containsSpecialChars = binding.etLastName.text?.matches(".*[^A-Za-z0-9].*".toRegex())
        if (binding.etLastName.text.toString().isEmpty()) {
            setError(binding.tiLastName, context ,"Campo obligatorio", background, binding.etLastName)
        } else if (containsSpecialChars==true) {
            setError(binding.tiLastName, context ,"El apellid no puede contener caracteres especiales", background, binding.etLastName)
        } else {
            removeError(binding.tiLastName, context,binding.etLastName)
            return true
        }
        return false
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupAdress(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        val containsSpecialChars = binding.etAdress.text?.matches(".*[^A-Za-z0-9áéíóúÁÉÍÓÚüÜ ,].*".toRegex())
        if (binding.etAdress.text.toString().isEmpty()) {
            setError(binding.tiAdress, context ,"Campo obligatorio", background, binding.etAdress)
        } else if (containsSpecialChars==true) {
            setError(binding.tiAdress, context ,"La dirección no puede contener caracteres especiales", background, binding.etAdress)
        } else {
            removeError(binding.tiAdress, context,binding.etAdress)
            return true
        }
        return false
    }


    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupNumberPhone(context: Context, binding: ActivityRegisterBinding, background: Int):Boolean {
        if (binding.etNumberPhone.text.toString().isEmpty()) {
            setError(binding.tiNumberPhone, context ,"Campo obligatorio", background, binding.etNumberPhone)
        } else if (binding.etNumberPhone.text.toString().length!=9){
            setError(binding.tiNumberPhone, context ,"Este campo debe tener 9 carácteres", background, binding.etNumberPhone)
        }else{
            removeError(binding.tiAdress, context,binding.etAdress)
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


    private fun addClient(){//Este metodo añadirá el cliente a la base de datos

    }
}