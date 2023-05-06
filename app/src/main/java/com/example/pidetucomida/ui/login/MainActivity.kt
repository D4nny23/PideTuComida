package com.example.pidetucomida.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pidetucomida.R
import com.example.pidetucomida.data.ApiService
import com.example.pidetucomida.ui.register.RegisterActivity
import com.example.pidetucomida.databinding.ActivityMainBinding
import com.example.pidetucomida.ui.content.ContentScreenActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    }

    private fun setupListener() {
        binding.mbCheckIn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

            binding.etEmail.setText("")
            binding.etPass.setText("")
        }

        binding.mbEnter.setOnClickListener {
            viewModel.findClientByEmail(
                binding.etEmail.text.toString(), binding.etPass.text.toString()
            ).observe(this) { success ->
                if (success) {
                    startActivity(Intent(this, ContentScreenActivity::class.java))
                } else {
                    Toast.makeText(this, "Correo o contrseña incorrecto", Toast.LENGTH_SHORT).show()
                }

            }
        }


        binding.mbSkip.setOnClickListener {
            startActivity(Intent(this, ContentScreenActivity::class.java))
        }
    }
}



