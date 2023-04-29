package com.example.pidetucomida.ui.register

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityRegisterBinding
import com.example.pidetucomida.ui.login.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterActivityViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[RegisterActivityViewModel::class.java]
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
        var email:Boolean; var pass:Boolean;var name:Boolean;var lastName:Boolean;var address:Boolean;var number:Boolean
        binding.mbRegister.setOnClickListener {
            val background = ContextCompat.getColor(this@RegisterActivity, R.color.dark_red)
            email= viewModel.setupEmail(this,binding,background)
            pass= viewModel.setupPassword(this,binding,background)
            name=viewModel.setupName(this,binding,background)
            lastName=viewModel.setupLastName(this,binding,background)
            address=viewModel.setupAdress(this,binding,background)
            number=viewModel.setupNumberPhone(this,binding,background)

            if(email && pass && name && lastName && address && number){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }

}