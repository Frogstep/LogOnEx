package io.ilyasin.logonex.data

import io.ilyasin.logonex.data.network.ProductData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IProductsRepository {
    suspend fun getAllProducts(): Flow<List<ProductData>>
    suspend fun getProductsByCategory(category: String): Flow<List<ProductData>>
    fun downloadProducts()
    fun progressState(): StateFlow<LoadingState>
}