package com.example.pidetucomida.ui.cart

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pidetucomida.R
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.data.database.ProductDatabase
import com.example.pidetucomida.databinding.ActivityCartBinding
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.ui.cart.adapter.CartAdapter
import com.example.pidetucomida.ui.cart.adapter.CartViewHolder

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        val db = Room.databaseBuilder(this, ProductDatabase::class.java, "Product_db").build()
        val dao= db.dao
        val repository= RepositoryCartProduct(dao)
        viewModel= CartActivityViewModel(repository)
        binding= ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getProducts()

        setupObservables()
        setupToolbar()
        setupListener()
    }

    private fun setupListener() {

    }

    private fun setupToolbar(){

        binding.tvTitle.visibility=View.VISIBLE
        binding.tvTitle.text= getString(R.string.order_summary)

        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservables(){
        viewModel.products.observe(this){productList->
            if(productList.size>0){
                updateAdapter(productList)
                getAdapter(productList)
                viewModel.getTotalPrice()
            }

        }
        viewModel.price.observe(this){price ->
            binding.tvTotal.text= "Precio total:\t"+price.toString()
        }
    }

    private fun updateAdapter( modelList: MutableList<Product>) {
        (binding.rvCart.adapter as CartAdapter?)?.apply {
            addAll(modelList)
        }
    }

    private fun getAdapter(productList: MutableList<Product> ) {
        binding.rvCart.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val myProductsAdapter = CartAdapter(
            productList, this, object : CartViewHolder.OnClickListener{
                override fun onClick(id: Int) {

                }

            }
        )
        binding.rvCart.adapter = myProductsAdapter
    }
}