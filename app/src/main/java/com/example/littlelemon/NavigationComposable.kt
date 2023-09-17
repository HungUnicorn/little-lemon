package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navController: NavHostController, database: AppDatabase) {
    val hasUserData = hasUserDataInSharedPreferences()

    NavHost(
        navController = navController,
        startDestination = if (hasUserData) OnboardingDestination.route else HomeDestination.route
    ) {
        composable(OnboardingDestination.route) { Onboarding(navController = navController) }
        composable(HomeDestination.route) { Home(navController = navController, database) }
        composable(ProfileDestination.route) { Profile(navController = navController) }
    }
}

@Composable
fun hasUserDataInSharedPreferences(): Boolean {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    val email = sharedPreferences.getString(EMAIL, "") ?: ""
    return email.isNotBlank()
}
