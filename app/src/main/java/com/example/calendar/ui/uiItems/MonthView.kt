package com.example.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.calendar.calendarItems.LunarDate
import com.example.calendar.ui.uiItems.monthGridCells
import com.example.calendar.viewModel.CalendarViewModel
import com.example.calendar.viewModel.DayLunarInfo
import java.time.LocalDate
import java.time.YearMonth

private val WEEKDAY_LABELS = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
private val DAY_NUMBER_SIZE = 28.dp
private val HOLIDAY_DOT_SIZE = 4.dp

fun lunarCellLabel(lunar: LunarDate): String =
    if (lunar.day == 1) "${lunar.day}/${lunar.month}" else lunar.day.toString()

@Composable
fun MonthView(
    month: YearMonth,
    today: LocalDate,
    viewModel: CalendarViewModel,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cells = remember(month) { monthGridCells(month) }

    val monthLunarData by produceState(initialValue = emptyMap<LocalDate, DayLunarInfo>(), month) {
        value = viewModel.getOrComputeMonthData(month)
    }

    Column(modifier = modifier) {
        WeekdayHeader()
        LazyVerticalGrid(columns = GridCells.Fixed(WEEKDAY_LABELS.size)) {
            items(cells) { date ->
                if (date == null) {
                    EmptyCell()
                } else {
                    val info = monthLunarData[date]
                    val lunar = info?.lunarDate ?: LunarDate(1, 1, date.year, false)
                    val isHoliday = info?.isHoliday ?: false
                    DayCell(
                        date = date,
                        lunar = lunar,
                        isHoliday = isHoliday,
                        isToday = date == today,
                        onClick = { onDayClick(date) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyCell() {
    Spacer(modifier = Modifier.aspectRatio(1f))
}

@Composable
private fun DayCell(
    date: LocalDate,
    lunar: LunarDate,
    isHoliday: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.aspectRatio(1f).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        DayLabels(date.dayOfMonth, lunar, isHoliday, isToday)
    }
}

@Composable
private fun WeekdayHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        WEEKDAY_LABELS.forEach { label ->
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun DayLabels(dayOfMonth: Int, lunar: LunarDate, isHoliday: Boolean, isToday: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DayNumber(dayOfMonth, isToday)
        Text(text = lunarCellLabel(lunar), style = MaterialTheme.typography.labelSmall)
        HolidayDot(isHoliday)
    }
}

@Composable
private fun DayNumber(day: Int, isToday: Boolean) {
    val background = if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isToday) MaterialTheme.colorScheme.onPrimary else Color.Unspecified

    Box(
        modifier = Modifier.size(DAY_NUMBER_SIZE).background(background, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = day.toString(), color = textColor, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun HolidayDot(isHoliday: Boolean) {
    val color = if (isHoliday) MaterialTheme.colorScheme.tertiary else Color.Transparent

    Box(modifier = Modifier.size(HOLIDAY_DOT_SIZE).background(color, CircleShape))
}


