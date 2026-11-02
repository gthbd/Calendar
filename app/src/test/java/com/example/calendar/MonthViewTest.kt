package com.example.calendar

import com.example.calendar.calendarItems.LunarDate
import org.junit.Assert.assertEquals
import org.junit.Test

class MonthViewTest {

    @Test
    fun lunarCellLabel_firstDayOfMonth_showsDayAndMonth() {
        val lunar = LunarDate(day = 1, month = 3, year = 2026, isLeapMonth = false)

        assertEquals("1/3", lunarCellLabel(lunar))
    }

    @Test
    fun lunarCellLabel_otherDay_showsDayOnly() {
        val lunar = LunarDate(day = 15, month = 3, year = 2026, isLeapMonth = false)

        assertEquals("15", lunarCellLabel(lunar))
    }
}