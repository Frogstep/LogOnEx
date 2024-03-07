package io.ilyasin.logonex.domain

import io.ilyasin.logonex.data.IProductsRepository
import io.ilyasin.logonex.data.network.ProductData
import io.ilyasin.logonex.ui.screens.products_screen.ProductsScreen
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting products by category. Used by [ProductsScreen]
 */
class ProductsByCategoryUseCase @Inject constructor(private val productsRepository: IProductsRepository){

    suspend fun getProductsByCategory(category: String): Flow<List<ProductData>> {
        return productsRepository.getProductsByCategory(category)
    }
}