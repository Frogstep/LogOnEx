package io.ilyasin.logonex.data.network

/**
 * Data class representing a product. Used to display the product data in the UI
 */
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
){
    fun formattedPrice(): String{
        val formatted = String.format("%.2f", price)
        return if(formatted.endsWith(".00")){
            formatted.substring(0, formatted.length - 3)
        }else if(formatted.endsWith(".0")){
            formatted.substring(0, formatted.length - 2)
        }else formatted
    }
}