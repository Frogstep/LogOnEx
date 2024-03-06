package io.ilyasin.logonex.ui.screens.products_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ilyasin.logonex.data.network.ProductData
import io.ilyasin.logonex.domain.ProductsByCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsUseCase: ProductsByCategoryUseCase) : ViewModel() {

    private val _products: MutableState<List<ProductData>> = mutableStateOf(emptyList())
    val products: State<List<ProductData>> = _products


    fun getProductsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.Default) {
            productsUseCase.getProductsByCategory(category).collect { products ->
                withContext(Dispatchers.Main) {
                    _products.value = products
                }
            }
        }
    }
}