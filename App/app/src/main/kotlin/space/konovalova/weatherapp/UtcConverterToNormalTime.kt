package space.konovalova.weatherapp

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class UtcConverterToNormalTime {
    fun utcToNormalTime(time: Int, format: String = "k:mm"): String {
        val offset: Int = 3
        // parse the time zone
        val zoneOffset: ZoneOffset = ZoneOffset.ofHours(offset)
        // create a moment in time from the given timestamp (in seconds!)
        val instant = Instant.ofEpochSecond(time.toLong())
        // define a formatter using the given pattern and a Locale
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        // then make the moment in time consider the zone and return the formatted String
        return instant.atOffset(zoneOffset).format(formatter)
    }
}