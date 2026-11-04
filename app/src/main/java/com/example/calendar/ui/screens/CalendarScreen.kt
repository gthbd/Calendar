package com.example.calendar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calendar.MonthView
import com.example.calendar.R
import com.example.calendar.ui.uiItems.MONTH_PAGE_COUNT
import com.example.calendar.ui.uiItems.MonthYearPickerDialog
import com.example.calendar.ui.uiItems.monthOfPage
import com.example.calendar.ui.uiItems.pageOfMonth
import com.example.calendar.viewModel.CalendarViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

fun monthTitle(month: YearMonth): String = "Tháng ${month.monthValue} năm ${month.year}"

@Composable
fun CalendarScreen(
    today: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = pageOfMonth(YearMonth.from(today))) { MONTH_PAGE_COUNT }
    val visibleMonth = monthOfPage(pagerState.currentPage)
    val coroutineScope = rememberCoroutineScope()
    val viewModel: CalendarViewModel = viewModel()

    // Hộp thoại chọn tháng/năm
    var showPicker by remember { mutableStateOf(false) }
    if (showPicker) {
        MonthYearPickerDialog(
            visibleMonth = visibleMonth,
            onDismiss = { showPicker = false },
            onMonthSelected = { targetMonth ->
                showPicker = false
                val targetPage = pageOfMonth(targetMonth)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(targetPage)
                }
            }
        )
    }


    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Nút chuyển về tháng trước
            CalendarNavButton(
                iconRes = R.drawable.arrow_upward,
                contentDescription = "Previous month",
                rotation = 270f,
                alignment = Alignment.CenterStart,
                enabled = pagerState.currentPage > 0
            ) {
                coroutineScope.launch {
                    if (pagerState.currentPage > 0) {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            }

            // Tiêu đề tháng năm
/*            Text(
                text = monthTitle(visibleMonth),
                modifier = Modifier
                    .weight(1f)
                    .clickable { showPicker = true }
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                )
*/

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {showPicker = true}
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = monthTitle(visibleMonth),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id=R.drawable.arrow_dropdown),
                    contentDescription = "dropdown",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            // Nút chuyển sang tháng sau
            CalendarNavButton(
                iconRes = R.drawable.arrow_upward,
                contentDescription = "Next month",
                rotation = 90f,
                alignment = Alignment.CenterEnd,
                enabled = pagerState.currentPage < MONTH_PAGE_COUNT - 1
                ) {
                    coroutineScope.launch {
                        if (pagerState.currentPage < MONTH_PAGE_COUNT - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            }

        HorizontalPager(state = pagerState) { page ->
            MonthView(
                month = monthOfPage(page),
                today = today,
                viewModel = viewModel,
                onDayClick = { date ->
                    onDayClick(date)
                },
            )
        }
    }
}

@Composable
private fun CalendarNavButton(
    iconRes: Int,
    contentDescription: String,
    rotation: Float,
    alignment: Alignment,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(horizontal = 14.dp)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = alignment
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier
                .rotate(rotation)
                .size(48.dp),
            tint = if (enabled) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}