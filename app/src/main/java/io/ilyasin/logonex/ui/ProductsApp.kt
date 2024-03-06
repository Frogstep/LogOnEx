package io.ilyasin.logonex.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.ilyasin.logonex.ui.screens.categories_screen.CategoriesScreen
import io.ilyasin.logonex.ui.screens.products_screen.ProductsScreen

@Composable
fun ProductsApp() {
    GlobalNavigation()
}


@Composable
fun GlobalNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavHost(navController = navController, startDestination = Screen.Categories.route) {
        composable(route = Screen.Categories.route) {
            CategoriesScreen(navController = navController)
        }
        composable(
            "${Screen.Products.route}/{$PRODUCT_SCREEN_PARAMETER}", arguments = listOf(navArgument(PRODUCT_SCREEN_PARAMETER)
            { type = NavType.StringType })
        ) {
            navBackStackEntry?.arguments?.getString(PRODUCT_SCREEN_PARAMETER)?.let { category ->
                ProductsScreen(category, navController = navController)
            }
        }
    }
}