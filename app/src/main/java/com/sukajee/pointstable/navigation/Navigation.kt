package com.sukajee.pointstable.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sukajee.pointstable.ui.features.enterdata.EnterDataScreen
import com.sukajee.pointstable.ui.features.enterdata.EnterDataViewModel
import com.sukajee.pointstable.ui.features.main.BottomSheet
import com.sukajee.pointstable.ui.features.main.MainScreen
import com.sukajee.pointstable.ui.features.main.MainViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }
        composable(
            Screen.EnterDataScreen.route.plus("/{seriesId}"),
            arguments = listOf(navArgument("seriesId") { type = NavType.IntType})
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