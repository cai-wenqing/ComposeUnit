package com.zcrain.composeunit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zcrain.composeunit.ui.BigPosterScreen
import com.zcrain.composeunit.ui.CodeInputScreen
import com.zcrain.composeunit.ui.HomeScreen
import com.zcrain.composeunit.ui.LoadingScreen

/**
 * @Author:CWQ
 * @DATE:2023/10/7
 * @DESC:
 */
object NavigationConfig {

    const val ROUTE_HOME = "home"

    const val ROUTE_LOADING = "LoadingView"

    const val ROUTE_CODE_INPUT = "CodeInputView"

    const val ROUTE_BIG_POSTER = "BigPoster"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationConfig.ROUTE_HOME
    ) {
        composable(NavigationConfig.ROUTE_HOME) {
            HomeScreen(navController)
        }
        composable(NavigationConfig.ROUTE_LOADING) {
            LoadingScreen()
        }
        composable(NavigationConfig.ROUTE_CODE_INPUT) {
            CodeInputScreen()
        }
        composable(NavigationConfig.ROUTE_BIG_POSTER) {
            BigPosterScreen()
        }
    }
}

class NavigationActions(private val navController: NavController) {

    fun navStart(path: String) {
        when (path) {
            NavigationConfig.ROUTE_HOME -> navController.navigate(NavigationConfig.ROUTE_HOME)
            NavigationConfig.ROUTE_LOADING -> navController.navigate(NavigationConfig.ROUTE_LOADING)
            NavigationConfig.ROUTE_CODE_INPUT -> navController.navigate(NavigationConfig.ROUTE_CODE_INPUT)
            NavigationConfig.ROUTE_BIG_POSTER -> navController.navigate(NavigationConfig.ROUTE_BIG_POSTER)
        }
    }
}