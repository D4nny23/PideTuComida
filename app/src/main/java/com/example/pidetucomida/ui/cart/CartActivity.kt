package com.example.pidetucomida.ui.cart

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityCartBinding
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        binding= ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar(){

        binding.tvTitle.visibility=View.VISIBLE
        binding.tvTitle.text= getString(R.string.order_summary)

        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}