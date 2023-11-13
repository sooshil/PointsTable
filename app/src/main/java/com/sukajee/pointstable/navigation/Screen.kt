package com.sukajee.pointstable.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen(route = "home")
}