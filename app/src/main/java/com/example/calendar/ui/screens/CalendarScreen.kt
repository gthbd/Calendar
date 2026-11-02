package com.example.calendar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendar.MonthView
import com.example.calendar.ui.uiItems.DayDetailSheet
import com.example.calendar.ui.uiItems.MONTH_PAGE_COUNT
import com.example.calendar.ui.uiItems.monthOfPage
import com.example.calendar.ui.uiItems.pageOfMonth
import java.time.LocalDate
import java.time.YearMonth

fun monthTitle(month: YearMonth): String = "Tháng ${month.monthValue} năm ${month.year}"

@Composable
fun CalendarScreen(today: LocalDate, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = pageOfMonth(YearMonth.from(today))) { MONTH_PAGE_COUNT }
    val visibleMonth = monthOfPage(pagerState.currentPage)
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(modifier = modifier) {
        Text(
            text = monthTitle(visibleMonth),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
        HorizontalPager(state = pagerState) { page ->
            MonthView(
                month = monthOfPage(page),
                today = today,
                onDayClick = { selectedDate = it },
            )
        }
    }

    selectedDate?.let { date ->
        DayDetailSheet(date = date, onDismiss = { selectedDate = null })
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    MaterialTheme {
        CalendarScreen(today = LocalDate.of(2026, 11, 2))
    }
}