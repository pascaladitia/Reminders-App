package com.example.medicationreminder.ui.navigation

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash")
    object LoginScreen: Screen("login")
    object RegisterScreen: Screen("register")

    object HomeScreen: Screen("home")
    object ProfileScreen: Screen("profile")
}