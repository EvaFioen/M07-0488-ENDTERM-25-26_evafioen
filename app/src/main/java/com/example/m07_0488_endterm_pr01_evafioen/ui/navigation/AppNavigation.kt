package com.example.m07_0488_endterm_pr01_evafioen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.m07_0488_endterm_pr01_evafioen.ui.screens.*
import com.example.m07_0488_endterm_pr01_evafioen.ui.screens.DetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onRocketClick = { rocketId ->
                    navController.navigate(Routes.createDetailRoute(rocketId))
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("rocketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rocketId = backStackEntry.arguments?.getString("rocketId")
            requireNotNull(rocketId)

            DetailScreen(
                rocketId = rocketId,
                onBackToHome = {
                    navController.popBackStack()
                }
            )
        }
    }
}
