package com.sukajee.pointstable.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sukajee.pointstable.ui.features.enterdata.EnterDataScreen
import com.sukajee.pointstable.ui.features.enterdata.EnterDataViewModel
import com.sukajee.pointstable.ui.features.main.MainScreen
import com.sukajee.pointstable.ui.features.main.MainViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        composable(
            route = Screen.EnterDataScreen.route.plus("/{seriesId}"),
            arguments = listOf(navArgument("seriesId") { type = NavType.IntType}),
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ) {backStackEntry ->
            val enterDataViewModel: EnterDataViewModel = hiltViewModel()
            val seriesId = backStackEntry.arguments?.getInt("seriesId")
            EnterDataScreen(
                navController = navController,
                viewModel = enterDataViewModel,
                seriesId = seriesId
            )
        }
    }
}

fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.7f else 1.2f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(1000, delayMillis = 90),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(1000, delayMillis = 90))
}

fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.7f else 1.2f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 90
        ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = 90))
}

enum class ScaleTransitionDirection {
    INWARDS, OUTWARDS
}