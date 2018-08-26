package net.youthdev.vievie.core.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {
  val currentTimestamp: Long
    get() = System.currentTimeMillis()

  fun getEndOfDay(date: Date): Date {
    val localDateTime = dateToLocalDateTime(date)
    val endOfDay = localDateTime.with(LocalTime.MAX)
    return localDateTimeToDate(endOfDay)
  }

  fun getStartOfDay(date: Date): Date {
    val localDateTime = dateToLocalDateTime(date)
    val startOfDay = localDateTime.with(LocalTime.MIN)
    return localDateTimeToDate(startOfDay)
  }

  private fun localDateTimeToDate(startOfDay: LocalDateTime): Date {
    return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant())
  }

  private fun dateToLocalDateTime(date: Date): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())
  }

  fun coordinatesFromPoint(fromLat: Double, fromLong: Double, bearing: Double, distance: Long): Map.Entry<Double, Double> {
    val d = distance / 6371000.0
    val b = Math.toRadians(bearing)
    val la = Math.toRadians(fromLat)
    val lo = Math.toRadians(fromLong)
    val toLat = Math.asin(Math.sin(la) * Math.cos(d) + Math.cos(la) * Math.sin(d) * Math.cos(b))
    var toLong = lo + Math.atan2(Math.sin(b) * Math.sin(d) * Math.cos(la), Math.cos(d) - Math.sin(la) * Math.sin(toLat))
    toLong = (toLong + 3 * Math.PI) % (2 * Math.PI) - Math.PI

    return AbstractMap.SimpleImmutableEntry(Math.toDegrees(toLat), Math.toDegrees(toLong))
  }

  fun getCurrentDateTime(pattern: String): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern))
  }

  fun convertToVnPeriod(time: String): String {
    return time
        .replace("am", "Sáng", ignoreCase = true)
        .replace("pm", "Chiều", ignoreCase = true)
  }
}