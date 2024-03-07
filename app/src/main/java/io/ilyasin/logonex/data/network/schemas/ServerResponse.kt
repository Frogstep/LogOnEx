package io.ilyasin.logonex.data.network.schemas

import com.google.gson.annotations.SerializedName

/**
 * Data class to represent the server response
 */
data class ServerResponse(@SerializedName("products") val productApiData: List<ProductApiData>)
