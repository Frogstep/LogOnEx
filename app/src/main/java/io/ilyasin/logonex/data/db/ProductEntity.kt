package io.ilyasin.logonex.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.ilyasin.logonex.data.network.ProductData
import io.ilyasin.logonex.data.network.schemas.Product

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


fun Product.toProductEntity() : ProductEntity {
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

