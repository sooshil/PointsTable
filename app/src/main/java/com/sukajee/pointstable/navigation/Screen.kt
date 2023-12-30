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

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object EnterDataScreen : Screen(route = "enter_data")
    data object AddEditSeriesScreen : Screen(route = "add_edit_series")
    data object PointsTableScreen : Screen(route = "points_table")
    data object DataEntryHelpScreen : Screen(route = "data_entry_help")
}