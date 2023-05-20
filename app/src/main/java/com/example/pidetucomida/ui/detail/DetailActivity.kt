package com.example.pidetucomida.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityDetailBinding
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.Constants

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        val product= intent.getSerializableExtra(Constants.PRODUCT_RESPONSE) as ProductResponse

        setupToolbar(product)
        setupView(product)
    }

    private fun setupToolbar(product : ProductResponse){
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)
        binding.toolBar.tvTitle.text=product.nombre
        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupView(product: ProductResponse){
        Glide.with(this)
            .asBitmap()
            .load(product.img)
            .apply(RequestOptions().override(1000,1000 ))
            .centerCrop()
            .into(binding.image)

        binding.tvTitleProduct.text=product.nombre
    }
}