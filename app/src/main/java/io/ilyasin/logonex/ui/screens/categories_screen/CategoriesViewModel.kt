package io.ilyasin.logonex.ui.screens.categories_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ilyasin.logonex.data.LoadingState
import io.ilyasin.logonex.data.network.CategoryData
import io.ilyasin.logonex.domain.CategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val categoriesUseCase: CategoriesUseCase) : ViewModel() {

    private val _categories: MutableState<List<CategoryData>> = mutableStateOf(emptyList())
    val categories: State<List<CategoryData>> = _categories

    private val _state: MutableState<LoadingState> = mutableStateOf(LoadingState.Initialized)
    val state: State<LoadingState> = _state

    init {
        listenToProgressState()
        categoriesUseCase.downloadProducts()
        getCategories()
    }

    private fun listenToProgressState() {
        viewModelScope.launch(Dispatchers.Default) {
            categoriesUseCase.progressState().collect {
                withContext(Dispatchers.Main) {
                    _state.value = it
                }
            }
        }
    }

    fun reDownloadProducts() {
        categoriesUseCase.downloadProducts()
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesUseCase.getCategories().collect {
                withContext(Dispatchers.Main) {
                    _categories.value = it
                }
            }
        }
    }
}