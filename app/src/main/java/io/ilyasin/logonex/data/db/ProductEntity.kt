package io.ilyasin.logonex.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.ilyasin.logonex.data.network.ProductData
import io.ilyasin.logonex.data.network.schemas.ProductApiData

/**
 * Table for storing products
 */
@Entity(tableName = "products")
data class ProductEntity(@PrimaryKey val id: Int,
                         @ColumnInfo(name = "title") val title: String,
                         @ColumnInfo(name = "description") val description: String,
                         @ColumnInfo(name = "price") val price: Double,
                         @ColumnInfo(name = "discountPercentage") val discountPercentage: Double,
                         @ColumnInfo(name = "rating") val rating: Double,
                         @ColumnInfo(name = "stock") val stock: Int,
                         @ColumnInfo(name = "brand") val brand: String,
                         @ColumnInfo(name = "category") val category: String,
                         @ColumnInfo(name = "thumbnail") val thumbnail: String,
                         @ColumnInfo(name = "images") val images: List<String>)


/*
    In this application these classes are identical, but I still prefer to follow the principle of separation
    of concerns by employing distinct classes for network, database, and UI. This approach promotes better code maintainability,
    re-usability, and testability in the long run.
 */
fun ProductApiData.toProductEntity() : ProductEntity {
    return ProductEntity(
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
}

fun ProductEntity.toProductData() : ProductData {
    return ProductData(
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
}

