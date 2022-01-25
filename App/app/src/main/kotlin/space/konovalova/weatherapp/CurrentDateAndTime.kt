package space.konovalova.weatherapp

import java.text.SimpleDateFormat
import java.util.*

class CurrentDateAndTime {
    var dateTimeObject = Date()
    val formatForTime = SimpleDateFormat("k:mm ")
    val formatForDate = SimpleDateFormat("dd.MM.yyyy")

    fun getCurrentDate(): String{
        dateTimeObject = Date()
        var currentDate: String = formatForDate.format(dateTimeObject)
        return currentDate

    }
    fun getCurrentTime(): String{
        dateTimeObject = Date()
        var currentTime: String = formatForTime.format(dateTimeObject)
        return currentTime
    }
}