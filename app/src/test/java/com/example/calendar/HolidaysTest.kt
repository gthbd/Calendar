package com.example.calendar

import com.example.calendar.calendarItems.LunarDate
import com.example.calendar.calendarItems.holidaysOf
import com.example.calendar.calendarItems.lunarHolidayOf
import com.example.calendar.calendarItems.solarHolidayOf
import com.example.calendar.calendarItems.toLunar
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class HolidaysTest {

    @Test
    fun solarHolidayOf_nationalDay_returnsName() {
        assertEquals("Quốc khánh", solarHolidayOf(LocalDate.of(2026, 9, 2)))
    }

    @Test
    fun solarHolidayOf_sameDateInAnotherYear_returnsSameName() {
        assertEquals("Tết Dương lịch", solarHolidayOf(LocalDate.of(2031, 1, 1)))
    }

    @Test
    fun solarHolidayOf_ordinaryDay_returnsNull() {
        assertNull(solarHolidayOf(LocalDate.of(2026, 3, 3)))
    }

    @Test
    fun solarHolidayOf_leapDay_doesNotThrow() {
        assertNull(solarHolidayOf(LocalDate.of(2024, 2, 29)))
    }

    @Test
    fun lunarHolidayOf_midAutumnFestival_returnsName() {
        val lunar = LunarDate(day = 15, month = 8, year = 2029, isLeapMonth = false)

        assertEquals("Tết Trung Thu", lunarHolidayOf(lunar))
    }

    @Test
    fun lunarHolidayOf_sameDayInLeapMonth_returnsNull() {
        val lunar = LunarDate(day = 15, month = 8, year = 2029, isLeapMonth = true)

        assertNull(lunarHolidayOf(lunar))
    }

    @Test
    fun lunarHolidayOf_ordinaryLunarDay_returnsNull() {
        val lunar = LunarDate(day = 2, month = 8, year = 2029, isLeapMonth = false)

        assertNull(lunarHolidayOf(lunar))
    }

    @Test
    fun lunarHolidayOf_hungKingsCommemoration2029_returnsName() {
        assertEquals("Giỗ Tổ Hùng Vương", lunarHolidayOf(LocalDate.of(2029, 4, 23).toLunar()))
    }

    @Test
    fun lunarHolidayOf_kitchenGodsDay2029_belongsToPreviousLunarYear() {
        assertEquals("Ông Công Ông Táo", lunarHolidayOf(LocalDate.of(2029, 2, 6).toLunar()))
    }

    @Test
    fun holidaysOf_lunarNewYearsEve2029_returnsOnlyNewYearsEve() {
        assertEquals(listOf("Giao thừa"), holidaysOf(LocalDate.of(2029, 2, 12)))
    }

    @Test
    fun holidaysOf_lunarNewYearDay2029_returnsOnlyLunarNewYear() {
        assertEquals(listOf("Tết Nguyên Đán"), holidaysOf(LocalDate.of(2029, 2, 13)))
    }

    @Test
    fun holidaysOf_ordinaryDay_returnsEmptyList() {
        assertEquals(emptyList<String>(), holidaysOf(LocalDate.of(2026, 2, 20)))
    }

    @Test
    fun holidaysOf_lastSupportedDay_doesNotThrow() {
        holidaysOf(LocalDate.of(2100, 12, 31))
    }
}