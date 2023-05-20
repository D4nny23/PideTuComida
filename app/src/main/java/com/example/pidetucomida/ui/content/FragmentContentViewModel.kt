package com.example.pidetucomida.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryProduct
import com.example.pidetucomida.model.product.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentContentViewModel: ViewModel() {

    private val repository = RepositoryProduct()

    private val _productsByType= MutableLiveData<MutableList<ProductResponse>>()
    val productsByType: LiveData<MutableList<ProductResponse>> = _productsByType

    private val _loadingFormState= MutableLiveData<Boolean>()
    val loadingFormState: LiveData<Boolean> = _loadingFormState

    fun getProductsByType(type: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loadingFormState.postValue(true)
            val response= repository.getProductsByType(type)
            if (response!=null) {
                _productsByType.postValue(response)
                _loadingFormState.postValue(false)
            }
        }
    }
}