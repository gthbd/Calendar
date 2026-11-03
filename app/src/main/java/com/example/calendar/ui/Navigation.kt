package com.example.calendar.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.calendar.ui.screens.CalendarScreen
import com.example.calendar.ui.screens.DayDetailScreen
import java.time.LocalDate

// 1. Định nghĩa các màn hình (Key)
sealed interface Screen {
    data object Calendar : Screen
    data class DayDetail(val date: LocalDate) : Screen
}

// 2. Composable quản lý Navigation chính của app
@Composable
fun CalendarApp(today: LocalDate, modifier: Modifier = Modifier) {
    // Khởi tạo back stack bắt đầu bằng màn hình Calendar
    val backStack = remember { mutableStateListOf<Any>(Screen.Calendar) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { key ->
            when (key) {
                is Screen.Calendar -> NavEntry(key) {
                    CalendarScreen(
                        today = today,
                        modifier = modifier,
                        onDayClick = { selectedDate ->
                            // Khi bấm vào ngày, thêm key DayDetail vào back stack để chuyển trang
                            backStack.add(Screen.DayDetail(selectedDate))
                        }
                    )
                }

                is Screen.DayDetail -> NavEntry(key) {
                    DayDetailScreen(
                        date = key.date,
                        onBackClick = {
                            // Khi bấm nút back, xóa key ở đỉnh stack để quay lại Calendar
                            backStack.removeLastOrNull()
                        },
                        modifier = modifier
                    )
                }

                else -> error("Unknown screen: $key")
            }
        }
    )
}