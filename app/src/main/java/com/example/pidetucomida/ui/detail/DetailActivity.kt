package com.example.pidetucomida.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityDetailBinding
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.ui.detail.adapter.IngredientsAdapter
import com.example.pidetucomida.utils.Constants

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailActivityViewModel
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        viewModel = ViewModelProvider(this)[DetailActivityViewModel::class.java]

        setupObservables()
        val productId = intent.getIntExtra(Constants.PRODUCT_ID, 0)
        viewModel.searchProductById(productId)
        viewModel.searchIngredientsByIdProduct(productId)
        setupListener()

    }

    private fun setupToolbar(product: ProductResponse) {
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)
        binding.toolBar.tvTitle.text = product.nombre
        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupViewProduct(product: ProductResponse) {
        Glide.with(this)
            .asBitmap()
            .load(product.img)
            .apply(RequestOptions().override(1000, 1000))
            .centerCrop()
            .into(binding.image)

        binding.tvTitleProduct.text = product.nombre
        binding.tvPrice.text= product.precio.toString()+getString(R.string.euro)
        binding.tvDescrption.text= product.descripcion
    }

    private fun setupObservables() {
        viewModel.productsById.observe(this) { product ->
            setupViewProduct(product)
            setupToolbar(product)
        }

        viewModel.loadingFormState.observe(this) { isLoading ->
            if(isLoading) {
                binding.pbDetail.visibility = View.VISIBLE
                binding.tvLoading.text= getString(R.string.loading)
                binding.tvLoading.visibility=View.VISIBLE
            }else{
                binding.pbDetail.visibility = View.GONE
                binding.tvLoading.visibility=View.GONE
            }
        }

        viewModel.ingByProduct.observe(this){ listIngredients ->
            getAdapter(listIngredients)
        }
    }

    private fun getAdapter(listIngredients: MutableList<IngredientResponse>){
        binding.rvIngredients.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val myIngredientsAdapter = IngredientsAdapter(
            listIngredients,
            this
        )
        binding.rvIngredients.adapter = myIngredientsAdapter
    }

    private fun setupListener(){
        binding.floatingActionButton.setOnClickListener{

        }
    }
}