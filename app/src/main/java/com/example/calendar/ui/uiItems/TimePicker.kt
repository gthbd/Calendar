package com.example.calendar.ui.uiItems

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.calendar.R
import com.example.calendar.calendarItems.SUPPORTED_YEARS
import java.time.YearMonth

@Composable
fun MonthYearPickerDialog(
    visibleMonth: YearMonth,
    onDismiss: () -> Unit,
    onMonthSelected: (YearMonth) -> Unit
) {
    var selectedYear by remember { mutableStateOf(visibleMonth.year) }
    var selectedMonth by remember { mutableStateOf(visibleMonth.monthValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chọn tháng và năm",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Thanh điều hướng Năm
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (selectedYear > SUPPORTED_YEARS.first) selectedYear-- },
                        enabled = selectedYear > SUPPORTED_YEARS.first
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_upward),
                            contentDescription = "Năm trước",
                            modifier = Modifier.rotate(270f)
                        )
                    }

                    Text(
                        text = "Năm $selectedYear",
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(
                        onClick = { if (selectedYear < SUPPORTED_YEARS.last) selectedYear++ },
                        enabled = selectedYear < SUPPORTED_YEARS.last
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_upward),
                            contentDescription = "Năm sau",
                            modifier = Modifier.rotate(90f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lưới 12 tháng (3 hàng x 4 cột)
                val months = (1..12).toList()
                months.chunked(3).forEach { rowMonths ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        rowMonths.forEach { month ->
                            val isSelected = (month == selectedMonth)
                            val targetYM = YearMonth.of(selectedYear, month)
                            // Kiểm tra xem tháng/năm có nằm trong dải hỗ trợ không
                            val isSupported = targetYM.year in SUPPORTED_YEARS

                            OutlinedButton(
                                onClick = { selectedMonth = month },
                                enabled = isSupported,
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isSelected)
                                        MaterialTheme.colorScheme.primaryContainer
                                    else
                                        MaterialTheme.colorScheme.surface
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(2.dp)
                            ) {
                                Text(
                                    text = "T$month",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    else
                                        MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val targetMonth = YearMonth.of(selectedYear, selectedMonth)
                    onMonthSelected(targetMonth)
                }
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}