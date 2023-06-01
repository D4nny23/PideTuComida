package com.example.pidetucomida.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartActivityViewModel(private val repository: RepositoryCartProduct) : ViewModel() {

    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>> = _products

    private val _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price
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

    fun insertOrder(){

    }

}