package com.jari.hrmsmobile.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.medicationreminder.ui.navigation.Screen

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)
