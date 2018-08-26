package net.youthdev.vievie.framework.jacksonextension

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.apache.commons.lang3.BooleanUtils

class BooleanDeserializer: JsonDeserializer<Boolean>() {

  override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext?): Boolean {
    return BooleanUtils.toBoolean(jsonParser.text)
  }
}