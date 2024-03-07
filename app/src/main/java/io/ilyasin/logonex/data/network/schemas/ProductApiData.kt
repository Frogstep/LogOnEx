package io.ilyasin.logonex.data.network.schemas

import com.google.gson.annotations.SerializedName

/**
 * Data class to represent the product data received from the server
 */
data class ProductApiData(@SerializedName("id") val id: Int,
                          @SerializedName("title") val title: String,
                          @SerializedName("description") val description: String,
                          @SerializedName("price") val price: Double,
                          @SerializedName("discountPercentage") val discountPercentage: Double,
                          @SerializedName("rating") val rating: Double,
                          @SerializedName("stock") val stock: Int,
                          @SerializedName("brand") val brand: String,
                          @SerializedName("category") val category: String,
                          @SerializedName("thumbnail") val thumbnail: String,
                          @SerializedName("images") val images: List<String>)
