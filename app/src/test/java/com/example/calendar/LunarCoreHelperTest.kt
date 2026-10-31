package com.example.calendar

import com.appota.lunarcore.LunarCoreHelper
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class LunarCoreHelperTest {

    // Vietnamese lunar rules are defined on the UTC+7 meridian, so the library needs it explicitly.
    private val vietnamTimeZone = 7.0

    @Test
    fun solarToLunar_lunarNewYear2026_isDay1OfMonth1() {
        val lunar = LunarCoreHelper.convertSolar2Lunar(17, 2, 2026, vietnamTimeZone)

        assertArrayEquals(intArrayOf(1, 1, 2026, 0), lunar)
    }

    @Test
    fun solarToLunar_day2OfLeapMonth2In2023_isFlaggedLeap() {
        val lunar = LunarCoreHelper.convertSolar2Lunar(23, 3, 2023, vietnamTimeZone)

        assertArrayEquals(intArrayOf(2, 2, 2023, 1), lunar)
    }
}