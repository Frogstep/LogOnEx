package io.ilyasin.logonex.domain

import io.ilyasin.logonex.data.IProductsRepository
import io.ilyasin.logonex.data.LoadingState
import io.ilyasin.logonex.data.network.CategoryData
import io.ilyasin.logonex.ui.screens.categories_screen.CategoriesScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for [CategoriesScreen] screen. It provides categories data and allows to re/download products and listen to progress state
 */
class CategoriesUseCase @Inject constructor(private val productsRepository: IProductsRepository) {

    /**
     * Get all products. group them by category and count distinct products and total products in each category
     */
    suspend fun getCategories(): Flow<List<CategoryData>> {
        return productsRepository.getAllProducts().map { productList ->
            productList.groupBy { it.category }.map { (category, products) ->
                val distinctProducts = products.distinctBy { product -> product.title }.size
                val totalProducts = products.size
                CategoryData(
                    category,
                    products[0].thumbnail,
                    distinctProducts,
                    totalProducts
                )//We know at least one product exists in each category
            }
        }
    }

    /**
     * Download products from server
     */
    fun downloadProducts() {
        productsRepository.downloadProducts()
    }

    /**
     * Listen to progress state
     */
    fun progressState(): StateFlow<LoadingState> {
        return productsRepository.progressState()
    }
}