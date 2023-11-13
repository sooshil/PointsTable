package com.sukajee.pointstable.ui.features.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Text("This is the sample text.")
}