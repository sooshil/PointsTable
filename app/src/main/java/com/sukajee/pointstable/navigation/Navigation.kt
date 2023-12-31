/*
 * Copyright (c) 2023, Sushil Kafle. All rights reserved.
 *
 * This file is part of the Android project authored by Sushil Kafle.
 * Unauthorized copying and using of a part or entirety of the code in this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 * For inquiries, please contact: info@sukajee.com
 * Last modified by Sushil on Friday, 29 Dec, 2023.
 */

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
import com.sukajee.pointstable.ui.features.addeditseries.AddEditSeriesScreen
import com.sukajee.pointstable.ui.features.addeditseries.AddEditSeriesViewModel
import com.sukajee.pointstable.ui.features.dataentryhelp.DataEntryHelpScreen
import com.sukajee.pointstable.ui.features.dataentryhelp.DataEntryHelpViewModel
import com.sukajee.pointstable.ui.features.enterdata.EnterDataScreen
import com.sukajee.pointstable.ui.features.enterdata.EnterDataViewModel
import com.sukajee.pointstable.ui.features.main.MainScreen
import com.sukajee.pointstable.ui.features.main.MainViewModel
import com.sukajee.pointstable.ui.features.table.PointsTableScreen
import com.sukajee.pointstable.ui.features.table.PointsTableViewModel

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
            arguments = listOf(navArgument("seriesId") { type = NavType.IntType }),
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
        ) { backStackEntry ->
            val enterDataViewModel: EnterDataViewModel = hiltViewModel()
            val seriesId = backStackEntry.arguments?.getInt("seriesId")
            EnterDataScreen(
                navController = navController,
                viewModel = enterDataViewModel,
                seriesId = seriesId
            )
        }
        composable(
            route = Screen.AddEditSeriesScreen.route.plus("?seriesId={seriesId}"),
            arguments = listOf(
                navArgument("seriesId") {
                    type = NavType.IntType
                    nullable = false
                    defaultValue = -1
                }
            ),
            enterTransition = {
                scaleIntoContainer(ScaleTransitionDirection.OUTWARDS)
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
        ) { backStackEntry ->
            val addEditSeriesViewModel: AddEditSeriesViewModel = hiltViewModel()
            val seriesId = backStackEntry.arguments?.getInt("seriesId")
            AddEditSeriesScreen(
                navController = navController,
                viewModel = addEditSeriesViewModel,
                seriesId = seriesId
            )
        }
        composable(
            route = Screen.PointsTableScreen.route.plus("?seriesId={seriesId}"),
            arguments = listOf(
                navArgument("seriesId") {
                    type = NavType.IntType
                    nullable = false
                    defaultValue = -1
                }
            ),
            enterTransition = {
                scaleIntoContainer(ScaleTransitionDirection.OUTWARDS)
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
        ) { backStackEntry ->
            val viewModel: PointsTableViewModel = hiltViewModel()
            val seriesId = backStackEntry.arguments?.getInt("seriesId")
            PointsTableScreen(
                navController = navController,
                viewModel = viewModel,
                seriesId = seriesId
            )
        }
        composable(
            route = Screen.DataEntryHelpScreen.route,
            enterTransition = {
                scaleIntoContainer(ScaleTransitionDirection.OUTWARDS)
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
            val viewModel: DataEntryHelpViewModel = hiltViewModel()
            DataEntryHelpScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.7f else 1.2f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 90
        ),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(500, delayMillis = 90))
}

fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.7f else 1.2f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 90
        ),
        targetScale = targetScale
    ) + fadeOut(tween(durationMillis = 500, delayMillis = 90))
}

enum class ScaleTransitionDirection {
    INWARDS, OUTWARDS
}