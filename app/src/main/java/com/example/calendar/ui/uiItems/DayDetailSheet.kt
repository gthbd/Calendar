package com.example.calendar.ui.uiItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailSheet(date: LocalDate, onDismiss: () -> Unit) {
    val details = remember(date) { dayDetailsOf(date) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
        ) {
            Text(text = details.weekday, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Dương lịch: ${details.solarDate}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Âm lịch: ${details.lunarDate}", style = MaterialTheme.typography.bodyLarge)
            details.holidays.forEach { holiday ->
                Text(
                    text = holiday,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}