package net.youthdev.vievie.framework.jacksonextension

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat

class ISO8601LocalTimeDeserializer: JsonDeserializer<LocalTime>() {

  override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext?): LocalTime {
    val dateStr = jsonParser.text
    return LocalTime.parse(dateStr, DateTimeFormat.forPattern(DateTimePatterns.DISPLAY_DATE_PATTERN))
  }
}