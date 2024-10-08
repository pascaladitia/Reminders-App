package com.example.medicationreminder.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash")
    data object LoginScreen: Screen("login")
    data object RegisterScreen: Screen("register")

    data object HomeScreen: Screen("home")
    data object CreateScreen: Screen("create")
    data object ProfileScreen: Screen("profile")
}