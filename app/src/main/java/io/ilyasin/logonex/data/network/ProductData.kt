package io.ilyasin.logonex.data.network

import io.ilyasin.logonex.data.network.schemas.Product

data class ProductData(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)

fun Product.toProductData() = ProductData(
    id = id,
    title = title,
    description = description,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand,
    category = category,
    thumbnail = thumbnail,
    images = images
)