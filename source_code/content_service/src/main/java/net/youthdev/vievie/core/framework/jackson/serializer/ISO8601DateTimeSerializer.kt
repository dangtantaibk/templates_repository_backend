package net.youthdev.vievie.core.framework.jackson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.joda.time.DateTime

import java.io.IOException

class ISO8601DateTimeSerializer : JsonSerializer<DateTime>() {
  @Throws(IOException::class)
  override fun serialize(dateTime: DateTime, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
    jsonGenerator.writeString(dateTime.toString())
  }
}
