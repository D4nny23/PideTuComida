package com.example.pidetucomida.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pidetucomida.R
import com.example.pidetucomida.ui.register.RegisterActivity
import com.example.pidetucomida.databinding.ActivityMainBinding
import com.example.pidetucomida.ui.content.ContentScreenActivity
import com.example.pidetucomida.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PideTuComida)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setupListener()
        setupObservables()
    }

    private fun setupListener() {
        binding.mbRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

            binding.etEmail.setText("")
            binding.etPass.setText("")
        }

        binding.mbEnter.setOnClickListener {
            viewModel.findClientByEmail(binding.etEmail.text.toString(), binding.etPass.text.toString())
        }


        binding.mbSkip.setOnClickListener {
            intent.putExtra(Constants.LOGGEDIN, false)
            startActivity(Intent(this, ContentScreenActivity::class.java))
        }
    }

    private fun setupObservables(){
        viewModel.client.observe(this){ clientResponse ->
            if (clientResponse!=null){
                Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

}



