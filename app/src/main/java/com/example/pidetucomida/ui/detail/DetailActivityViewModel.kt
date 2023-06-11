package com.example.pidetucomida.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.data.RepositoryIngredient
import com.example.pidetucomida.data.RepositoryProduct
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivityViewModel(private val repositoryCart: RepositoryCartProduct) : ViewModel() {

    private val _productsById = MutableLiveData<ProductResponse>()
    val productsById: LiveData<ProductResponse> = _productsById

    private val _ingByProduct = MutableLiveData<MutableList<IngredientResponse>>()
    val ingByProduct: LiveData<MutableList<IngredientResponse>> = _ingByProduct

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    private val _loadingFormState = MutableLiveData<Boolean>()
    val loadingFormState: LiveData<Boolean> = _loadingFormState

    private val _setError = MutableLiveData<Int>()
    val setError: LiveData<Int> = _setError

    private val _setErrorToast = MutableLiveData<Int>()
    val setErrorToast: LiveData<Int> = _setErrorToast
    fun searchProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingFormState.postValue(true)
            val response = RepositoryProduct().getProductsById(id)
            when (response) {
                is Result.Success -> {
                    val responseData = response.data
                    _productsById.postValue(responseData)
                    _loadingFormState.postValue(false)
                }

                is Result.Error -> {
                    _setError.postValue(response.message)
                    _loadingFormState.postValue(false)
                }
            }
        }
    }



    fun searchIngredientsByIdProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = RepositoryIngredient().getIngredientsByIdProduct(id)){
                is Result.Success ->{
                    val responseData= response.data
                    _ingByProduct.postValue(responseData)
                }
                is Result.Error ->{
                    _setError.postValue(response.message)
                }
            }
        }
    }

    fun saveProduct(product: ProductResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val existProduct = repositoryCart.getProductById(product.idProducto)
            val productsBefore = repositoryCart.returnCountProducts()
            if (existProduct > 0) {
                _isSaved.postValue(true)
            } else {
                repositoryCart.insertProduct(product)
                val productsAfter = repositoryCart.returnCountProducts()
                if (productsAfter == productsBefore + 1) {
                    _isSaved.postValue(true)
                } else {
                    _isSaved.postValue(false)
                }
            }
            repositoryCart.updateQuantity(product.idProducto)
            repositoryCart.updateTotalPrice(product.idProducto)

        }
    }
}