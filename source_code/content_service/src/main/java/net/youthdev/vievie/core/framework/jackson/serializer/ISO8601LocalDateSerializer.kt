package net.youthdev.vievie.core.framework.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.joda.time.LocalDate

import java.io.IOException

class ISO8601LocalDateSerializer : JsonSerializer<LocalDate>() {

  @Throws(IOException::class)
  override fun serialize(value: LocalDate, gen: JsonGenerator,
                         arg2: SerializerProvider) {
    gen.writeString(value.toString(DATE_PATTERN))
  }

  companion object {
    private val DATE_PATTERN = "yyyy-MM-dd"
  }
}