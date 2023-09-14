package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navController: NavHostController, isUserDataStored: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isUserDataStored) OnboardingDestination.route else HomeDestination.route
    ) {
        composable(OnboardingDestination.route) { /* Onboarding Screen Composable */ }
        composable(HomeDestination.route) { /* Home Screen Composable */ }
        composable(ProfileDestination.route) { /* Profile Screen Composable */ }
    }
}