package io.ilyasin.logonex.ui

/**
 * Navigation routes for the app
 */
sealed class Screen(val route: String) {
    data object Categories : Screen("Categories")
    data object Products : Screen("Products")
}

const val PRODUCT_SCREEN_PARAMETER = "category"