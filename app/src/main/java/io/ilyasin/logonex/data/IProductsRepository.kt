package io.ilyasin.logonex.data

import io.ilyasin.logonex.data.network.ProductApiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IProductsRepository {
    suspend fun getAllProducts(): Flow<List<ProductApiData>>
    suspend fun getProductsByCategory(category: String): Flow<List<ProductApiData>>
    fun downloadProducts()
    fun progressState(): StateFlow<LoadingState>
}