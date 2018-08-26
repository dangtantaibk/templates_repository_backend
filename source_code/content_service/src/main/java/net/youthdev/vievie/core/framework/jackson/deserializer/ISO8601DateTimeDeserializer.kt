package net.youthdev.vievie.core.framework.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.apache.commons.lang3.StringUtils
import org.joda.time.DateTime

import java.io.IOException

class ISO8601DateTimeDeserializer : JsonDeserializer<DateTime>() {
  @Throws(IOException::class)
  override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): DateTime? {
    val dateTimeStr = jsonParser.text
    return if (StringUtils.isNotBlank(dateTimeStr)) {
      DateTime.parse(dateTimeStr)
    } else {
      null
    }
  }
}
