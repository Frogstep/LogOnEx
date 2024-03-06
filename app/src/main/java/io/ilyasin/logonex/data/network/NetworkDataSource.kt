package io.ilyasin.logonex.data.network

import io.ilyasin.logonex.data.network.schemas.ServerResponse
import java.io.IOException
import javax.inject.Inject

/**
 * Data source for network requests
 */
class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    companion object {
        const val DEFAULT_PRODUCTS_LIMIT = 100
    }

    suspend fun getProducts(limit: Int = DEFAULT_PRODUCTS_LIMIT): Result<ServerResponse> {
        return try {
            val response = apiService.products(limit)
            Result.success(response)
        } catch (ex: IOException) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}