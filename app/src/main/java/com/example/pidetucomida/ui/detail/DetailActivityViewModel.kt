package com.example.pidetucomida.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryProduct
import com.example.pidetucomida.model.product.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivityViewModel : ViewModel() {

    private val repositoryProduct = RepositoryProduct()

    private val _productsById =MutableLiveData<ProductResponse>()
    val productsById: LiveData<ProductResponse> = _productsById

    private val _loadingFormState= MutableLiveData<Boolean>()
    val loadingFormState: LiveData<Boolean> = _loadingFormState

    fun searchProductById(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loadingFormState.postValue(true)
            val response= repositoryProduct.getProductsById(id)
            if (response!=null){
                _productsById.postValue(response)
                _loadingFormState.postValue(false)
            }
        }
    }
}