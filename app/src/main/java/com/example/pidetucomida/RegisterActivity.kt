package com.example.pidetucomida

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.pidetucomida.databinding.ActivityMainBinding
import com.example.pidetucomida.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor= Color.TRANSPARENT
        setContentView(R.layout.activity_register)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        setupListener()
    }

    private fun setupToolBar(){
        binding.toolBar.tvTitle.visibility=View.VISIBLE
        binding.toolBar.tvTitle.text="Registro"
        binding.toolBar.tvTitle.setTextAppearance(this,R.style.TitleStyle)

        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupListener(){
        binding.mbRegister.setOnClickListener{

        }
    }
}