package io.ilyasin.logonex.data.network

/**
 * Data class representing a category. Used to display the category data in the UI
 */
data class CategoryData(val category: String, val imageUrl: String, val distinctProducts: Int, val totalSum: Int)