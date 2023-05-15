package com.example.pidetucomida.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.Repository
import com.example.pidetucomida.model.product.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentContentViewModel: ViewModel() {

    private val repository = Repository()

    private val _productsByType= MutableLiveData<MutableList<ProductResponse>>()
    val productsByType: LiveData<MutableList<ProductResponse>> = _productsByType


    fun getProductsByType(type: String){
        var response: MutableList<ProductResponse>
        viewModelScope.launch(Dispatchers.IO) {
            response= repository.getProductsByType(type)
            _productsByType.postValue(response)
        }
    }
}