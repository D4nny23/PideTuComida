package com.example.pidetucomida.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pidetucomida.data.RepositoryCartProduct
import com.example.pidetucomida.data.RepositoryIngredient
import com.example.pidetucomida.data.RepositoryProduct
import com.example.pidetucomida.model.Ingredient.IngredientResponse
import com.example.pidetucomida.model.product.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivityViewModel(private val repositoryCart: RepositoryCartProduct) : ViewModel() {

    private val _productsById =MutableLiveData<ProductResponse>()
    val productsById: LiveData<ProductResponse> = _productsById

    private val _ingByProduct =MutableLiveData<MutableList<IngredientResponse>>()
    val ingByProduct: LiveData<MutableList<IngredientResponse>> = _ingByProduct

    private val _isSaved =MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    private val _loadingFormState= MutableLiveData<Boolean>()
    val loadingFormState: LiveData<Boolean> = _loadingFormState

    fun searchProductById(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loadingFormState.postValue(true)
            val response= RepositoryProduct().getProductsById(id)
            if (response!=null){
                _productsById.postValue(response)
                _loadingFormState.postValue(false)
            }
        }
    }

    fun searchIngredientsByIdProduct(id: Int){
        viewModelScope.launch (Dispatchers.IO){
            val response= RepositoryIngredient().getIngredientsByIdProduct(id)
            _ingByProduct.postValue(response)
        }
    }

    fun saveProduct(product: ProductResponse){
        viewModelScope.launch (Dispatchers.IO){
            val existProduct= repositoryCart.getProductById(product.idProducto)
            if (existProduct>0){
                repositoryCart.updateQuantity(product.idProducto)
                _isSaved.postValue(true)
            }else{
                val products= repositoryCart.returnCountProducts()
                val response= repositoryCart.insertProduct(product).toInt()
                if (response == products+1){
                    _isSaved.postValue(true)
                }else{
                    _isSaved.postValue(false)
                }
            }

        }
    }
}