package com.example.pidetucomida.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryProduct
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentContentViewModel: ViewModel() {

    private val repository = RepositoryProduct()

    private val _productsByType= MutableLiveData<MutableList<ProductResponse>>()
    val productsByType: LiveData<MutableList<ProductResponse>> = _productsByType

    private val _loadingFormState= MutableLiveData<Boolean>()
    val loadingFormState: LiveData<Boolean> = _loadingFormState

    private val _setError= MutableLiveData<Int>()
    val setError: LiveData<Int> = _setError

    fun getProductsByType(type: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loadingFormState.postValue(true)
            when(val response= repository.getProductsByType(type)){
                is Result.Success ->{
                    val responseData = response.data
                    _productsByType.postValue(responseData)
                    _loadingFormState.postValue(false)
                }
                is Result.Error ->{
                    val error=response.message
                    _setError.postValue(error)
                    _loadingFormState.postValue(false)
                }
            }
        }
    }
}