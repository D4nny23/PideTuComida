package com.example.pidetucomida.ui.detail

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
import com.example.pidetucomida.ui.content.ContentFragment
import com.example.pidetucomida.ui.content.ContentScreenActivity
import com.example.pidetucomida.ui.content.FragmentContentViewModel
import com.example.pidetucomida.ui.detail.adapter.IngredientsAdapter
import com.example.pidetucomida.ui.login.MainActivity
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

        setupObservables()
        val productId = intent.getIntExtra(Constants.PRODUCT_ID, 0)
        viewModel.searchProductById(productId)
        viewModel.searchIngredientsByIdProduct(productId)


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
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }else{
                Toast.makeText(this, "Producto no guardado", Toast.LENGTH_SHORT).show()
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
        binding.floatingActionButton.setOnClickListener{
            viewModel.saveProduct(product)
        }
    }
}