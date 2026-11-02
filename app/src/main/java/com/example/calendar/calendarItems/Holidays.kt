package com.example.calendar.calendarItems

import java.time.LocalDate
import java.time.MonthDay

// bảng lễ dương lịch Việt Nam
private val SOLAR_HOLIDAYS: Map<MonthDay, String> = mapOf(
    MonthDay.of(1, 1) to "Tết Dương lịch",
    MonthDay.of(2, 14) to "Lễ Tình nhân",
    MonthDay.of(3, 8) to "Ngày Quốc tế Phụ nữ",
    MonthDay.of(4, 30) to "Ngày Giải phóng miền Nam",
    MonthDay.of(5, 1) to "Ngày Quốc tế Lao động",
    MonthDay.of(6, 1) to "Ngày Quốc tế Thiếu nhi",
    MonthDay.of(9, 2) to "Quốc khánh",
    MonthDay.of(10, 20) to "Ngày Phụ nữ Việt Nam",
    MonthDay.of(11, 20) to "Ngày Nhà giáo Việt Nam",
    MonthDay.of(12, 22) to "Ngày thành lập Quân đội nhân dân Việt Nam",
    MonthDay.of(12, 25) to "Lễ Giáng sinh",
)

fun solarHolidayOf(date: LocalDate): String? = SOLAR_HOLIDAYS[MonthDay.from(date)]

private data class LunarMonthDay(val month: Int, val day: Int)
// bảng lễ âm lịch Việt Nam
private val LUNAR_HOLIDAYS: Map<LunarMonthDay, String> = mapOf(
    LunarMonthDay(1, 1) to "Tết Nguyên Đán",
    LunarMonthDay(1, 15) to "Rằm tháng Giêng",
    LunarMonthDay(3, 3) to "Tết Hàn Thực",
    LunarMonthDay(3, 10) to "Giỗ Tổ Hùng Vương",
    LunarMonthDay(4, 15) to "Lễ Phật Đản",
    LunarMonthDay(5, 5) to "Tết Đoan Ngọ",
    LunarMonthDay(7, 7) to "Lễ Thất Tịch",
    LunarMonthDay(7, 15) to "Lễ Vu Lan",
    LunarMonthDay(8, 15) to "Tết Trung Thu",
    LunarMonthDay(10, 15) to "Tết Hạ Nguyên",
    LunarMonthDay(12, 23) to "Ông Công Ông Táo",
)


fun lunarHolidayOf(lunar: LunarDate): String? {
    // Why: a leap month repeats a regular month's number; holidays are observed in the regular month only.
    if (lunar.isLeapMonth) return null

    return LUNAR_HOLIDAYS[LunarMonthDay(lunar.month, lunar.day)]
}


private const val LUNAR_NEW_YEARS_EVE_NAME = "Giao thừa"
private fun isLunarNewYearsEve(date: LocalDate, lunar: LunarDate): Boolean {
    // Why: only day 29+ of month 12 can precede Tết; skipping the rest avoids converting day+1 beyond the supported years.
    if (lunar.month != 12 || lunar.day < 29) return false

    val tomorrow = date.plusDays(1).toLunar()
    return tomorrow.month == 1 && tomorrow.day == 1
}

fun holidaysOf(date: LocalDate, lunar: LunarDate = date.toLunar()): List<String> {
    return listOfNotNull(
        solarHolidayOf(date),
        lunarHolidayOf(lunar),
        LUNAR_NEW_YEARS_EVE_NAME.takeIf { isLunarNewYearsEve(date, lunar) },
    )
}