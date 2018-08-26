package net.youthdev.vievie.framework.jacksonextension

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import org.joda.time.LocalTime

class ISO8601LocalTimeSerializer: JsonSerializer<LocalTime>() {

  override fun serialize(time: LocalTime?, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider?) {
    jsonGenerator.writeString(time?.toString(DateTimePatterns.TIME_PATTERN))
  }
}