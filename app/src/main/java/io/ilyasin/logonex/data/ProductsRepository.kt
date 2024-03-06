package io.ilyasin.logonex.data

import io.ilyasin.logonex.data.db.ProductDatabase
import io.ilyasin.logonex.data.db.toProductData
import io.ilyasin.logonex.data.db.toProductEntity
import io.ilyasin.logonex.data.network.NetworkDataSource
import io.ilyasin.logonex.data.network.ProductApiData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val database: ProductDatabase
) : IProductsRepository {

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Initialized)

    override fun downloadProducts() {
        _loadingState.value = LoadingState.InProgress
        CoroutineScope(Dispatchers.IO).launch {
            val result = networkDataSource.getProducts()
            if (result.isSuccess && result.getOrNull() != null) {
                _loadingState.value = LoadingState.Success
                database.productDao().replaceProducts(result.getOrNull()!!.products.map { it.toProductEntity() })
            } else {
                _loadingState.value = LoadingState.Failure(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    override fun progressState() = _loadingState.asStateFlow()


    override suspend fun getAllProducts(): Flow<List<ProductApiData>> {
        return database.productDao().getAllProducts().map { entities ->
            entities.map { entity ->
                entity.toProductData()
            }
        }
    }

    override suspend fun getProductsByCategory(category: String): Flow<List<ProductApiData>> {
        return database.productDao().getProductsWithCategory(category).map { entities ->
            entities.map { entity ->
                entity.toProductData()
            }
        }
    }
}