package io.ilyasin.logonex.domain

import io.ilyasin.logonex.data.IProductsRepository
import io.ilyasin.logonex.data.LoadingState
import io.ilyasin.logonex.data.network.CategoryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val productsRepository: IProductsRepository) {
    suspend fun getCategories(): Flow<List<CategoryData>> {
        return productsRepository.getAllProducts().map {
            it.groupBy { it.category }.map { (category, products) ->
                val distinctProducts = products.distinctBy { product -> product.title }.size
                val totalProducts = products.size
                CategoryData(category, products[0].thumbnail, distinctProducts, totalProducts)
            }
        }
    }

    fun downloadProducts() {
        productsRepository.downloadProducts()
    }

    fun progressState(): StateFlow<LoadingState> {
        return productsRepository.progressState()
    }
}