package com.example.pidetucomida.ui.login

import android.content.Context
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
        goToContentActivity()
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
            startActivity(Intent(this, ContentScreenActivity::class.java))
        }
    }

    private fun setupObservables() {
        viewModel.client.observe(this) { clientResponse ->
            if (clientResponse != null) {
                val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                clientResponse.apply {
                    editor.putString("nombre", nombre)
                    editor.putString("apellido", apellido)
                    editor.putString("correo", correo)
                    editor.putInt("id", idCliente)
                    editor.putString("pass", pass)
                    editor.putString("direccion", direccionEnvio)
                    editor.putString("numero", telefono)
                }
                editor.apply()
                startActivity(Intent(this, ContentScreenActivity::class.java))
            } else {
                Toast.makeText(this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.setError.observe(this){
            when(it){
                R.string.time_out_exception ->{
                    binding.tvNoConnection.visibility = View.VISIBLE
                    binding.tvNoConnection.text = getString(it)
                    binding.ivNoConnection.visibility = View.VISIBLE

                    binding.tlEmail.visibility=View.GONE
                    binding.tlPass.visibility=View.GONE
                    binding.mbEnter.visibility=View.GONE
                    binding.mbRegister.visibility=View.GONE
                    binding.imageView.visibility=View.GONE
                    binding.mbSkip.visibility=View.GONE
                    binding.tvNoHaveAccount.visibility=View.GONE
                }
                R.string.connect_exception ->{
                    binding.tvNoConnection.visibility = View.VISIBLE
                    binding.tvNoConnection.text = getString(it)
                    binding.ivNoConnection.visibility = View.VISIBLE
                    binding.ivNoConnection.setImageResource(R.drawable.ic_no_connection)
                }
                else ->{
                    binding.ivNoConnection.visibility = View.GONE
                    binding.tvNoConnection.visibility = View.GONE
                }
            }
        }
    }

    private fun goToContentActivity(){
        val preferences= getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val email = preferences.getString("correo", "").toString()
        if (email.isNotEmpty()){
            startActivity(Intent(this, ContentScreenActivity::class.java))
        }
    }

}



