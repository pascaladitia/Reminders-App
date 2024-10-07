package com.example.medicationreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicationreminder.ui.navigation.RouteScreen
import com.example.medicationreminder.ui.theme.MedicationReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationReminderTheme(
                dynamicColor = false,
                darkTheme = true
            ) {
                RouteScreen()
            }
        }
    }
}