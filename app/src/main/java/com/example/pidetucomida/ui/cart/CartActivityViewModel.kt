package com.example.pidetucomida.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.OrderRepository
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.model.Product
import com.example.pidetucomida.model.order.Order
import com.example.pidetucomida.model.order.OrderProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivityViewModel(private val repository: RepositoryCartProduct) : ViewModel() {

    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>> = _products

    private val _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    private val _order = MutableLiveData<Boolean>()
    val order: LiveData<Boolean> = _order

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.postValue(repository.getProducts())
        }
    }

    fun getTotalPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTotalPrice()
            _price.postValue(response)
        }
    }

    fun removeProduct(id: Int, precio: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val quantity = repository.getQuantityById(id)
            if (quantity > 1) {
                repository.updateTotalPriceWhenRemoveProduct(precio, id)
                repository.removeQuantity(id)
            } else if (quantity >= 1) {
                repository.deleteProduct(id)
            }
            getProducts()
        }
    }

    fun addQuantityProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addQuantityProduct(product.idProducto)
            repository.updateTotalPriceWhenAddProduct(product.precio,product.idProducto)
            getProducts()
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    suspend fun insertOrder(o: Order, totalProductsInCart: ArrayList<Product>){
        withContext(Dispatchers.IO) {
            val response= OrderRepository().insertOrder(o)
            if (response>0){
                insertProductInOrder(totalProductsInCart,response)
            }else{
                _order.postValue(false)

            }
        }
    }
    private suspend fun insertProductInOrder(totalProductsInCart: ArrayList<Product>, response:Int){
        withContext(Dispatchers.IO) {
            try {
                totalProductsInCart.forEach { product ->
                    var order= OrderProducts(response, product.idProducto, product.cantidad)
                    OrderRepository().insertProductsInOrder(order)
                }
                _order.postValue(true)
            } catch (e: Exception) {
                _order.postValue(false)
            }
        }

    }

}