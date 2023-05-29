package com.example.pidetucomida.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.model.Product
import kotlinx.coroutines.launch

class CartActivityViewModel(private val repository: RepositoryCartProduct) : ViewModel() {

    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>> = _products
    fun getProducts() {
        viewModelScope.launch {
            val response=repository.getProducts()
            _products.postValue(response)
        }
    }


}