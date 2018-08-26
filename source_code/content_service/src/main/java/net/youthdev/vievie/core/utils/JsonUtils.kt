package net.youthdev.vievie.core.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import net.youthdev.vievie.core.exception.WrappedAppError
import org.apache.commons.logging.LogFactory

import java.io.IOException

object JsonUtils {
  private val LOG = LogFactory.getLog(JsonUtils::class.java)

  private var sObjectMapper: ObjectMapper? = null

  init {
    sObjectMapper = ObjectMapper()
    sObjectMapper!!.registerModule(JodaModule())
    sObjectMapper!!.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  fun <T> fromJson(jsonString: String, targetType: Class<T>): T {
    try {
      return sObjectMapper!!.readValue(jsonString, targetType)
    } catch (ex: IOException) {
      throw WrappedAppError(ex)
    }
  }

  fun toJson(obj: Any): String {
    try {
      return sObjectMapper!!.writeValueAsString(obj)
    } catch (ex: JsonProcessingException) {
      throw WrappedAppError(ex)
    }
  }

  fun isValidJson(json: String): Boolean {
    return try {
      sObjectMapper!!.readTree(json)
      true
    } catch (ex: Exception) {
      LOG.error("Error while checking json validation", ex)
      false
    }
  }

}
