package com.example.pidetucomida.ui.content

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityContentScreenBinding
import com.example.pidetucomida.ui.login.MainActivityViewModel

class ContentScreenActivity : AppCompatActivity() {

    private lateinit var viewModel: ContentViewModel

    private lateinit var binding: ActivityContentScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        binding= ActivityContentScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this)[ContentViewModel::class.java]
        setupToolbar()

        setupView()
        setupObservable()
        viewModel.searchProducts()

    }

    private fun setupToolbar(){
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)

        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupView(){
        val adapter = ViewPagerAdapterContent(supportFragmentManager, lifecycle)

//        binding.wpMain.adapter=adapter

    }

    private fun setupObservable(){
        viewModel.listProduct.observe(this) { productList ->
            productList.let {
                    binding.textView.text=productList.joinToString(",")

            }
        }
    }

}