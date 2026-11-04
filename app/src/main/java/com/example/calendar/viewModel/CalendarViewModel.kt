package com.example.calendar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.calendarItems.LunarDate
import com.example.calendar.calendarItems.holidaysOf
import com.example.calendar.calendarItems.toLunar
import com.example.calendar.ui.uiItems.monthGridCells
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth


data class DayLunarInfo(
    val lunarDate: LunarDate,
    val isHoliday: Boolean = false,
    val isSelected: Boolean = false
)
typealias MonthLunarData = Map<LocalDate, DayLunarInfo>

class CalendarViewModel : ViewModel() {
    //bộ nhớ đệm lưu kết quả các tháng đã tính
    private val monthCache = mutableMapOf<YearMonth, MonthLunarData>()

    // Hàm tải dữ liệu cho từng MonthView
    private fun prefetchMonth(month: YearMonth) {
        if (!monthCache.containsKey(month)) {
            viewModelScope.launch(Dispatchers.Default) {
                val calculatedData = calculateMonth(month)
                monthCache[month] = calculatedData
            }
        }
    }

    private fun calculateMonth(month: YearMonth): MonthLunarData {
        //lấy danh sách các ô trong lưới tháng
        val cells = monthGridCells(month)
        //tính ngày âm và kiểm tra ngày lễ
        return cells.filterNotNull().associateWith { date ->
            val lunar = date.toLunar()
            val isHoliday = holidaysOf(date, lunar).isNotEmpty()
            DayLunarInfo(lunarDate = lunar, isHoliday = isHoliday)
        }
    }

    // Hàm cung cấp dữ liệu cho từng MonthView độc lập
    suspend fun getOrComputeMonthData(month: YearMonth): MonthLunarData {
        monthCache[month]?.let { return it }

        return withContext(Dispatchers.Default) {
            val calculatedData = calculateMonth(month)
            monthCache[month] = calculatedData

            prefetchMonth(month.plusMonths(2))
            prefetchMonth(month.minusMonths(2))

            calculatedData
        }
    }
}