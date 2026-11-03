package com.example.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.calendar.ui.CalendarApp
import com.example.calendar.ui.screens.CalendarScreen
import com.example.calendar.ui.theme.CalendarTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val today = LocalDate.now()
        setContent {
            CalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Gọi CalendarApp thay vì CalendarScreen
                    CalendarApp(
                        today = today,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}