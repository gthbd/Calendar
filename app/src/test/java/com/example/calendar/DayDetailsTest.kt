package com.example.calendar.ui.uiItems

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DayDetailsTest {

    @Test
    fun dayDetailsOf_ordinaryDay_hasNoHolidays() {
        val details = dayDetailsOf(LocalDate.of(2026, 11, 2))

        assertEquals(
            DayDetails(
                weekday = "Thứ Hai",
                solarDate = "ngày 2 tháng 11 năm 2026",
                lunarDate = "ngày 24 tháng 9 năm 2026",
                holidays = emptyList(),
            ),
            details,
        )
    }

    @Test
    fun dayDetailsOf_dayInLeapMonth_marksLeapMonth() {
        val details = dayDetailsOf(LocalDate.of(2023, 3, 23))

        assertEquals("ngày 2 tháng 2 nhuận năm 2023", details.lunarDate)
    }

    @Test
    fun dayDetailsOf_kitchenGodsDay2029_usesPreviousLunarYearAndListsHoliday() {
        val details = dayDetailsOf(LocalDate.of(2029, 2, 6))

        assertEquals("ngày 23 tháng 12 năm 2028", details.lunarDate)
        assertEquals(listOf("Ông Công Ông Táo"), details.holidays)
    }

    @Test
    fun dayDetailsOf_sunday_isChuNhat() {
        assertEquals("Chủ Nhật", dayDetailsOf(LocalDate.of(2026, 11, 1)).weekday)
    }
}