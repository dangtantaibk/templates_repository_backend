package net.youthdev.vievie.core.framework.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

import java.io.IOException

class ISO8601LocalDateDeserializer : JsonDeserializer<LocalDate>() {
  @Throws(IOException::class)
  override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): LocalDate {
    val dateStr = jsonParser.text
    return LocalDate.parse(dateStr, DateTimeFormat.forPattern(DateTimePatterns.DATE_PATTERN))
  }
}
