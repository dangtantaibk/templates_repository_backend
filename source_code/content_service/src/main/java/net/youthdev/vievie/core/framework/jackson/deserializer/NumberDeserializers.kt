package net.youthdev.vievie.framework.jacksonextension

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class NumberDeserializers {

  class LongDeserializer: JsonDeserializer<Long>() {
    override fun deserialize(jsonParser: JsonParser, deserializer: DeserializationContext?): Long? {
      val value = jsonParser.text
      return value.toLongOrNull()
    }
  }
}