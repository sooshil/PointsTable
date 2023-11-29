package com.sukajee.pointstable.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    }
}