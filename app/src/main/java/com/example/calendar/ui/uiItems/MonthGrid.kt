package com.example.calendar.ui.uiItems

import java.time.LocalDate
import java.time.YearMonth

fun monthGridCells(month: YearMonth): List<LocalDate?> {
    // Why: DayOfWeek.MONDAY is 1, so Monday-first weeks need exactly (value - 1) blanks before day 1.
    val leadingBlankCount = month.atDay(1).dayOfWeek.value - 1
    val days = (1..month.lengthOfMonth()).map { month.atDay(it) }

    return List<LocalDate?>(leadingBlankCount) { null } + days
}