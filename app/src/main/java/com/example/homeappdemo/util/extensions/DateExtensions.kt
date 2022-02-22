package com.example.homeappdemo.util.extensions

import android.widget.DatePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DefaultDateConfiguration {
    const val defaultDateFormat: String = "dd/MMM/yyyy"
    val dateType: DateType = DateType.MAX_DATE
}

fun Date.toDateString(targetFormart: String): String {
    val sdf = SimpleDateFormat(targetFormart)
    val calendar = Calendar.getInstance()
    calendar.time = this
    return sdf.format(calendar.time)
}

fun DatePicker.getDate(dateFormat: String): String {
    val day = this.dayOfMonth
    val month: Int = this.month
    val year: Int = this.year
    val calendar = Calendar.getInstance()
    calendar[year, month] = day
    val date = calendar.time
    return date.toDateString(dateFormat)
}

fun getPatternFromDate(
    date: String,
    inputPattern: String = DefaultDateConfiguration.defaultDateFormat,
    outputPattern: String = "MMMM dd, yyyy h:mm a"
): String {
    val originalFormat: DateFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
    val targetFormat = SimpleDateFormat(outputPattern)
    val formattedDate = originalFormat.parse(date)
    return targetFormat.format(formattedDate)
}

fun DatePicker.setToTodayDate() {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    this.updateDate(year, month, day)
}

enum class DateType {
    MAX_DATE, MIN_DATE
}