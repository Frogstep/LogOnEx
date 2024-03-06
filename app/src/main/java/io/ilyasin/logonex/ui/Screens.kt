package io.ilyasin.logonex.ui

sealed class Screen(val route: String) {
    data object Categories : Screen("Categories")
    data object Products : Screen("Products")
}

const val PRODUCT_SCREEN_PARAMETER = "category"