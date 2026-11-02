package com.example.calendar.ui.screens

import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Test

class CalendarScreenTest {

    @Test
    fun monthTitle_twoDigitMonth_showsMonthAndYear() {
        assertEquals("Tháng 11 năm 2026", monthTitle(YearMonth.of(2026, 11)))
    }

    @Test
    fun monthTitle_singleDigitMonth_hasNoLeadingZero() {
        assertEquals("Tháng 2 năm 2027", monthTitle(YearMonth.of(2027, 2)))
    }
}