package com.example.calendar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.calendar.R
import com.example.calendar.ui.uiItems.dayDetailsOf
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun DayDetailScreen(
    date: LocalDate,
    today: LocalDate,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val details = remember(date) { dayDetailsOf(date) }
    val daysBetween = remember(date, today) { ChronoUnit.DAYS.between(today, date) }
    val distanceText = when {
        daysBetween == 0L -> "Cách ngày hiện tại: Hôm nay (0 ngày)"
        daysBetween > 0L -> "Cách ngày hiện tại: Sau $daysBetween ngày"
        else -> "Cách ngày hiện tại: Trước ${-daysBetween} ngày"
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_upward),
                    contentDescription = "back button",
                    modifier = Modifier
                        .rotate(270f)
                        .size(32.dp)
                )
            }
            Text(
                text = "Chi tiết ngày",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 12.dp, end = 12.dp)
        ) {
            Text(text = details.weekday, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Dương lịch: ${details.solarDate}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = "Âm lịch: ${details.lunarDate}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = distanceText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            details.holidays.forEach { holiday ->
                Text(
                    text = holiday,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
