package com.example.calendar.calendarItems

import com.appota.lunarcore.LunarCoreHelper
import java.time.LocalDate

// mùi giờ việt nam là 7 UTC
private const val VIETNAM_TIME_ZONE = 7.0

// khoảng cách năm ứng dụng cho phép xem
val SUPPORTED_YEARS = 1900..2100

data class LunarDate(
    val day: Int,
    val month: Int,
    val year: Int,
    val isLeapMonth: Boolean,
)

fun LocalDate.toLunar(): LunarDate {
    require(year in SUPPORTED_YEARS) { "Year $year is outside $SUPPORTED_YEARS" }

    val (lunarDay, lunarMonth, lunarYear, leapFlag) =
        LunarCoreHelper.convertSolar2Lunar(dayOfMonth, monthValue, year, VIETNAM_TIME_ZONE)

    return LunarDate(lunarDay, lunarMonth, lunarYear, isLeapMonth = leapFlag == 1)
}