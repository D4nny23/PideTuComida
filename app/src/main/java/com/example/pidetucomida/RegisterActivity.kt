package com.example.pidetucomida

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.pidetucomida.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        setupListener()
    }

    private fun setupToolBar() {
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = "Registro"
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)

        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupListener() {
        binding.mbRegister.setOnClickListener {
            val background = ContextCompat.getColor(this@RegisterActivity, R.color.dark_red)
            setupEmail(background)
            setupContrsena(background)


        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupEmail(background: Int) {
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.tiEmail.error = "Campo obligatorio"
            binding.tiEmail.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
            binding.tiEmail.background =
                ContextCompat.getDrawable(this@RegisterActivity, R.drawable.errorborder)
            binding.tiEmail.isErrorEnabled=true
        } else if (!binding.etEmail.text.toString().contains('@')) {
            binding.tiEmail.error = "Este campo debe contener @"
            binding.tiEmail.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
            binding.tiEmail.background =
                ContextCompat.getDrawable(this@RegisterActivity, R.drawable.errorborder)
        } else {
            binding.tiEmail.error = null
            binding.tiEmail.setErrorIconDrawable(null)
            binding.tiEmail.background =
                ContextCompat.getDrawable(this@RegisterActivity, R.drawable.border)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupContrsena(background: Int) {

        val containsLetterUpperCase = binding.etPassword.text?.matches(".*[A-Z].*".toRegex())
        val containsNumbers = binding.etPassword.text?.matches(".*[0-9].*".toRegex())
        val containsSpecialChars = binding.etPassword.text?.matches(".*[^A-Za-z0-9].*".toRegex())
        if (binding.etPassword.text.toString().isEmpty()) {
            binding.tiPass.error = "Campo obligatorio"
            binding.tiPass.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
            binding.tiPass.background =
                ContextCompat.getDrawable(this@RegisterActivity, R.drawable.errorborder)
            binding.etPassword.compoundDrawableTintList = ColorStateList.valueOf(background)
        } else if (containsLetterUpperCase == false || containsNumbers == false || containsSpecialChars == false) {
            binding.tiPass.error =
                "La contraseña debe contener numeros, caracteres especiales y mayúsculas"
            binding.tiPass.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
            binding.tiPass.background =
                ContextCompat.getDrawable(this@RegisterActivity, R.drawable.errorborder)
            binding.etPassword.compoundDrawableTintList = ColorStateList.valueOf(background)
        } else {
            binding.tiPass.error = null
            binding.tiPass.setErrorIconDrawable(null)
            binding.tiPass.background =
                ContextCompat.getDrawable(this@RegisterActivity, R.drawable.border)
            binding.etPassword.compoundDrawableTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this@RegisterActivity, R.color.green))
        }
    }
}