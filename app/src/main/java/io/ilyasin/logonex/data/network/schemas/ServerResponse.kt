package io.ilyasin.logonex.data.network.schemas

import com.google.gson.annotations.SerializedName

data class ServerResponse(@SerializedName("products") val products: List<Product>)
