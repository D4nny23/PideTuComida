package com.example.pidetucomida.ui.cart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pidetucomida.R
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.data.database.ProductDatabase
import com.example.pidetucomida.databinding.ActivityCartBinding
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.model.order.Order
import com.example.pidetucomida.ui.cart.adapter.CartAdapter
import com.example.pidetucomida.ui.cart.adapter.CartViewHolder
import com.example.pidetucomida.ui.content.ContentScreenActivity
import com.example.pidetucomida.ui.login.MainActivity
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartActivityViewModel
    private var totalPrice: Double = 0.0
    private var orderOK=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        val db = Room.databaseBuilder(this, ProductDatabase::class.java, "Product_db").build()
        val dao = db.dao
        val repository = RepositoryCartProduct(dao)
        viewModel = CartActivityViewModel(repository)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getProducts()

        setupObservables()
        setupToolbar()
    }

    private fun setupListener(productList: MutableList<Product>) {
        val preferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val email = preferences.getString("correo", "")
        binding.mbBuy.setOnClickListener {
            if (email != "") {
                if (productList.size != 0) {
                    createOrder(productList)
                } else {
                    Toast.makeText(this, getString(R.string.empty_cart), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.obligatory_login),
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }


            binding.etComment.viewTreeObserver?.addOnGlobalLayoutListener {
                val r = Rect()
                binding.etComment.getWindowVisibleDisplayFrame(r)
                val screenHeight = binding.etComment.rootView.height
                val keypadHeight = screenHeight - r.bottom

                if (keypadHeight > screenHeight * 0.15) {
                    val params = binding.tiComment.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(0, 0, 0, 500)
                    binding.tiComment.layoutParams = params
                    binding.nScroll.postDelayed(
                        {
                            binding.nScroll.scrollTo(0, binding.tiComment.bottom)
                        },
                        200
                    )
                } else {
                    val params = binding.tiComment.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(0, 0, 0, 0)
                    binding.tiComment.layoutParams = params
                }
            }

    }

    private fun setupToolbar() {

        binding.tvTitle.visibility = View.VISIBLE
        binding.tvTitle.text = getString(R.string.order_summary)

        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservables() {
        viewModel.products.observe(this) { productList ->
            if (productList.size > 0) {
                updateAdapter(productList)
                getAdapter(productList)
                setupListener(productList)
                viewModel.getTotalPrice()
            } else {
                binding.tvTotal.visibility = View.GONE
                binding.rvCart.visibility = View.GONE
            }

        }
        viewModel.price.observe(this) { price ->

            val decimalFormat = DecimalFormat("#0.00")
            val formattedPrice = decimalFormat.format(price)
            binding.tvTotal.text =
                getString(R.string.total_price) + formattedPrice + getString(R.string.euro)

            totalPrice = price
        }

        viewModel.order.observe(this) {
            if (it) {
                orderOK=true
                Toast.makeText(this, getString(R.string.order_placed), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getText(R.string.order_refused), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getProduct.observe(this) { quantity ->
            viewModel.position.observe(this) {
                updateAdapterQuantity(it, quantity)
            }
        }

        viewModel.setError.observe(this) {
            when (it) {
                R.string.time_out_exception -> {
                    Toast.makeText(this, R.string.time_out_exception, Toast.LENGTH_SHORT).show()
                }
                R.string.connect_exception -> {
                    Toast.makeText(this, R.string.connect_exception, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateAdapter(modelList: MutableList<Product>) {
        (binding.rvCart.adapter as CartAdapter?)?.apply {
            addAll(modelList)
        }
    }

    private fun updateAdapterQuantity(position: Int, quantity:Int) {
        (binding.rvCart.adapter as CartAdapter?)?.apply {
            updateQuantity(position, quantity)
        }
        viewModel.getTotalPrice()
    }

    private fun getAdapter(productList: MutableList<Product>) {
        binding.rvCart.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val myAdapter = CartAdapter(
            productList, this, object : CartViewHolder.OnClickListener {
                override fun onClickRemove(product: Product, position: Int) {
                    viewModel.removeProduct(product, position)
                }

                override fun onClickAdd(product: Product, position: Int) {
                    viewModel.addQuantityProduct(product, position)
                }

            }
        )
        binding.rvCart.adapter = myAdapter
    }


    private fun getIdCliente(): Int {
        val preferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        return preferences.getInt("id", 0)
    }

    private fun alertDialog(productList: MutableList<Product>, selectedText: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle(R.string.make_order)
        builder.setMessage(R.string.confirm_order)
        builder.setPositiveButton(R.string.confirm) { _, _ ->
            lifecycleScope.launch {
                viewModel.insertOrder(
                    Order(
                        getIdCliente(),
                        binding.etComment.text.toString(),
                        selectedText,
                        totalPrice
                    ), productList as ArrayList<Product>
                )

                if (orderOK){
                    startActivity(Intent(this@CartActivity, ContentScreenActivity::class.java))
                    viewModel.deleteAll()
                    updateAdapter(productList)
                    binding.tvTotal.visibility = View.GONE
                }



            }
        }
        builder.setNegativeButton(R.string.deny) { _, _ ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun createOrder(productList: MutableList<Product>) {
        val selectedRadioButtonId = binding.rgPay.checkedRadioButtonId
        if (selectedRadioButtonId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedText = selectedRadioButton.text.toString()
            alertDialog(productList, selectedText)
        } else {
            Toast.makeText(this, getString(R.string.choose_way_to_pay), Toast.LENGTH_SHORT)
                .show()
        }

    }
}