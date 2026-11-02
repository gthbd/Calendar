package com.example.calendar.ui.uiItems

import com.example.calendar.calendarItems.SUPPORTED_YEARS
import java.time.YearMonth
import java.time.temporal.ChronoUnit

private val FIRST_MONTH = YearMonth.of(SUPPORTED_YEARS.first, 1)
private val LAST_MONTH = YearMonth.of(SUPPORTED_YEARS.last, 12)

val MONTH_PAGE_COUNT = ChronoUnit.MONTHS.between(FIRST_MONTH, LAST_MONTH).toInt() + 1

fun monthOfPage(page: Int): YearMonth {
    require(page in 0 until MONTH_PAGE_COUNT) { "Page $page is outside 0 until $MONTH_PAGE_COUNT" }

    return FIRST_MONTH.plusMonths(page.toLong())
}

fun pageOfMonth(month: YearMonth): Int {
    require(month in FIRST_MONTH..LAST_MONTH) { "Month $month is outside $FIRST_MONTH..$LAST_MONTH" }

    return ChronoUnit.MONTHS.between(FIRST_MONTH, month).toInt()
}