package com.example.pidetucomida.ui.detail

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pidetucomida.R
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.data.database.ProductDatabase
import com.example.pidetucomida.databinding.ActivityDetailBinding
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.ui.content.ContentScreenActivity
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

        val db = Room.databaseBuilder(this, ProductDatabase::class.java, "Product_db").build()
        val dao= db.dao
        val repository= RepositoryCartProduct(dao)
        viewModel= DetailActivityViewModel(repository)


        val productId = intent.getIntExtra(Constants.PRODUCT_ID, 0)
        setupObservables()
        viewModel.searchProductById(productId)



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
            setupListener(product)
        }

        viewModel.loadingFormState.observe(this) { isLoading ->
            if(isLoading) {
                binding.pbDetail.visibility = View.VISIBLE
                binding.tvLoading.visibility=View.VISIBLE
            }else{
                binding.pbDetail.visibility = View.GONE
                binding.tvLoading.visibility=View.GONE
            }
        }

        viewModel.ingByProduct.observe(this){ listIngredients ->
            getAdapter(listIngredients)
        }

        viewModel.isSaved.observe(this){ isSaved ->
            if (isSaved){
                Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ContentScreenActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Producto no guardado", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.setError.observe(this) { error ->
            when (error) {
                R.string.time_out_exception -> {
                    setupView(R.string.time_out_exception)
                }
                R.string.connect_exception -> {
                    setupView(R.string.connect_exception)
                    binding.ivNoConnection.setImageResource(R.drawable.ic_no_connection)
                }
                R.string.generic_error ->{
                    setupView(R.string.generic_error)
                }
                else -> {
                    binding.tvNoConnection.visibility = View.GONE
                    binding.ivNoConnection.visibility = View.GONE
                }
            }
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

    private fun setupListener(product: ProductResponse){
        viewModel.searchIngredientsByIdProduct(product.idProducto)
        binding.floatingActionButton.setOnClickListener{
            viewModel.saveProduct(product)
        }
    }

    private fun setupView(texto: Int){
        binding.tvNoConnection.text = getString(texto)
        binding.tvNoConnection.visibility = View.VISIBLE
        binding.ivNoConnection.visibility = View.VISIBLE
        binding.tvIngredients.visibility=View.GONE
        binding.divider.visibility=View.GONE
        binding.divider2.visibility=View.GONE
    }
}