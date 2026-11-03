package com.example.calendar.ui

import java.time.LocalDate

//define key for 2 screens
sealed interface Screen {
    data object Calendar : Screen
    data class DayDetail(val date: LocalDate) : Screen
}