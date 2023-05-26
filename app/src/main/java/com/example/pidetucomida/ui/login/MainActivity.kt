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
    }

    private fun setupListener() {
        binding.mbRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

            binding.etEmail.setText("")
            binding.etPass.setText("")
        }

        binding.mbEnter.setOnClickListener {
            viewModel.findClientByEmail(
                this, binding.etEmail.text.toString(), binding.etPass.text.toString()
            ) { isCorrectPassword ->
                if (isCorrectPassword) {
                    val i = Intent(this, ContentScreenActivity::class.java)
                    i.putExtra(Constants.LOGGEDIN, true)
                    startActivity(i)
                } else {
                    Toast.makeText(this, getString(R.string.incorrect_user_password), Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }


        binding.mbSkip.setOnClickListener {
            intent.putExtra(Constants.LOGGEDIN, false)
            startActivity(Intent(this, ContentScreenActivity::class.java))
        }
    }

}



