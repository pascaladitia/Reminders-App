package com.example.medicationreminder.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medicationreminder.ui.screen.home.HomeScreen
import com.example.medicationreminder.ui.screen.login.LoginScreen
import com.example.medicationreminder.ui.screen.profile.ProfileScreen
import com.example.medicationreminder.ui.screen.register.RegisterScreen
import com.example.medicationreminder.ui.screen.splash.SplashScreen

@Composable
fun RouteScreen(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(Screen.HomeScreen.route, Screen.ProfileScreen.route)) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(700))
            }
        ) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen(
                    paddingValues = paddingValues
                ) {
                    navController.navigate(
                        if (it) Screen.HomeScreen.route else Screen.LoginScreen.route
                    ) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(
                    paddingValues = paddingValues,
                    onLogin = {
                        navController.navigate(Screen.HomeScreen.route) {
                            popUpTo(Screen.LoginScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onRegister = {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                )
            }
            composable(route = Screen.RegisterScreen.route) {
                RegisterScreen(
                    paddingValues = paddingValues,
                    onNavBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen()
            }
        }
    }
}
