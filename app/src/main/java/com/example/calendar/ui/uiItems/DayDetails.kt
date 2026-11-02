package com.example.calendar.ui.uiItems

import com.example.calendar.calendarItems.holidaysOf
import com.example.calendar.calendarItems.toLunar
import java.time.LocalDate

private val WEEKDAY_NAMES = listOf("Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật")

data class DayDetails(
    val weekday: String,
    val solarDate: String,
    val lunarDate: String,
    val holidays: List<String>,
)

fun dayDetailsOf(date: LocalDate): DayDetails {
    val lunar = date.toLunar()
    // Why: DayOfWeek.MONDAY is 1, matching the Monday-first order of WEEKDAY_NAMES.
    val weekday = WEEKDAY_NAMES[date.dayOfWeek.value - 1]
    val leapSuffix = if (lunar.isLeapMonth) " nhuận" else ""

    return DayDetails(
        weekday = weekday,
        solarDate = "ngày ${date.dayOfMonth} tháng ${date.monthValue} năm ${date.year}",
        lunarDate = "ngày ${lunar.day} tháng ${lunar.month}$leapSuffix năm ${lunar.year}",
        holidays = holidaysOf(date, lunar),
    )
}