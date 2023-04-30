package com.example.pidetucomida.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.pidetucomida.R
import com.example.pidetucomida.data.ApiService
import com.example.pidetucomida.ui.register.RegisterActivity
import com.example.pidetucomida.databinding.ActivityMainBinding
import com.example.pidetucomida.ui.content.ContentScreenActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
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
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[MainActivityViewModel::class.java]
        setupListener()
//        searchUsers()
    }

    private fun setupListener(){
        binding.mbCheckIn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

            binding.etEmail.setText("")
            binding.etPass.setText("")
        }

        binding.mbEnter.setOnClickListener {
            val client= viewModel.findClientByEmail(binding.etEmail.text.toString())
            if(binding.etEmail.text.toString()==client.email){
                if(binding.etPass.text.toString()==client.pass){
                    startActivity(Intent(this, ContentScreenActivity::class.java))
                }
            }
        }

        binding.mbSkip.setOnClickListener {
            startActivity(Intent(this, ContentScreenActivity::class.java))
        }
    }

//    private fun getRetrofit(): Retrofit{
//        return Retrofit.Builder()
//            .baseUrl("http://localhost:8080/WebApplication2/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    private fun  searchUsers(){
//        CoroutineScope(Dispatchers.IO).launch {
//            val call= getRetrofit().create(ApiService::class.java).getProducts("resources/users/")
//            val users=call.body()
//            if (call.isSuccessful){
//                println(users.toString())
//                Log.d("EXITO", "")
//            }else{
//                //showError
//            }
//        }
//    }

}