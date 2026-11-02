package com.example.calendar

import com.example.calendar.ui.uiItems.MONTH_PAGE_COUNT
import com.example.calendar.ui.uiItems.monthOfPage
import com.example.calendar.ui.uiItems.pageOfMonth
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Test

class MonthPagesTest {

    @Test
    fun monthPageCount_covers201YearsOfMonths() {
        assertEquals(2412, MONTH_PAGE_COUNT)
    }

    @Test
    fun monthOfPage_firstPage_isJanuary1900() {
        assertEquals(YearMonth.of(1900, 1), monthOfPage(0))
    }

    @Test
    fun monthOfPage_lastPage_isDecember2100() {
        assertEquals(YearMonth.of(2100, 12), monthOfPage(MONTH_PAGE_COUNT - 1))
    }

    @Test
    fun pageOfMonth_november2026_is1522() {
        assertEquals(1522, pageOfMonth(YearMonth.of(2026, 11)))
    }

    @Test
    fun pageOfMonth_roundTripsEveryPage() {
        for (page in 0 until MONTH_PAGE_COUNT) {
            assertEquals(page, pageOfMonth(monthOfPage(page)))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun monthOfPage_negativePage_throws() {
        monthOfPage(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun pageOfMonth_monthBeforeSupportedRange_throws() {
        pageOfMonth(YearMonth.of(1899, 12))
    }
}