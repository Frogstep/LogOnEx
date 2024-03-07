package io.ilyasin.logonex.data

import io.ilyasin.logonex.data.network.ProductData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository interface for the product data
 */
interface IProductsRepository {
    /**
     * Flow for collecting products from the database
     */
    suspend fun getAllProducts(): Flow<List<ProductData>>

    /**
     * Flow for collecting products from the database by category
     */
    suspend fun getProductsByCategory(category: String): Flow<List<ProductData>>

    /**
     * Function to re/download the products from the server
     */
    fun downloadProducts()

    /**
     * Flow for collecting the progress state of the download
     */
    fun progressState(): StateFlow<LoadingState>
}