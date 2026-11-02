package com.example.calendar

import com.example.calendar.ui.uiItems.monthGridCells
import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Test

class MonthGridTest {

    @Test
    fun monthGridCells_monthStartingMonday_hasNoLeadingBlanks() {
        val cells = monthGridCells(YearMonth.of(2026, 6))

        assertEquals(LocalDate.of(2026, 6, 1), cells.first())
        assertEquals(30, cells.size)
    }

    @Test
    fun monthGridCells_monthStartingSunday_hasSixLeadingBlanks() {
        val cells = monthGridCells(YearMonth.of(2026, 11))

        assertEquals(List<LocalDate?>(6) { null }, cells.take(6))
        assertEquals(LocalDate.of(2026, 11, 1), cells[6])
        assertEquals(36, cells.size)
    }
}