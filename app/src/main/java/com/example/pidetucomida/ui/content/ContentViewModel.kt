package com.example.pidetucomida.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pidetucomida.data.Repository
import com.example.pidetucomida.model.product.ProductResponse
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class ContentViewModel : ViewModel() {

    private val repository = Repository()
    private val _listProduct = MutableLiveData<MutableList<ProductResponse>>()
    val listProduct: LiveData<MutableList<ProductResponse>>
        get() = _listProduct

    fun searchProducts() {
        viewModelScope.launch {
            _listProduct.value = repository.getProducts()
        }
    }
}