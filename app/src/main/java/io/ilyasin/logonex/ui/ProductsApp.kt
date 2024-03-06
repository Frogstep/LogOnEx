package io.ilyasin.logonex.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.ilyasin.logonex.ui.screens.categories_screen.CategoriesScreen
import io.ilyasin.logonex.ui.screens.categories_screen.CategoriesViewModel
import io.ilyasin.logonex.ui.screens.products_screen.ProductsScreen

@Composable
fun ProductsApp() {
    GlobalNavigation()
}


@Composable
fun GlobalNavigation() {

    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavHost(navController = navController, startDestination = "Categories") {
        composable("Categories") {
            CategoriesScreen(navController = navController, scrollState = scrollState)
        }
        composable(
            "Products/{category}", arguments = listOf(navArgument("category")
            { type = NavType.StringType })
        ) {

            navBackStackEntry?.arguments?.getString("category")?.let { category ->
                ProductsScreen(category, navController = navController, scrollState = scrollState)
            }
        }
    }
}