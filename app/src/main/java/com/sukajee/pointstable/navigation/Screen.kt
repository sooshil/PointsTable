package com.sukajee.pointstable.navigation

sealed class Screen(val route: String) {
    data object HomeScreen: Screen(route = "home")
    data object EnterDataScreen: Screen(route = "enter_data")
    data object AddEditSeriesScreen: Screen(route = "add_edit_series")
}