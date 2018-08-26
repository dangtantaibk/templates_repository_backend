package net.youthdev.vievie.application.exceptions.common

import net.youthdev.vievie.application.exception.Exception
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.framework.localization.MessageResolver

class InvalidFieldException(
    fieldName: String,
    allowableValues: List<String>
) : RestException(
    EXCEPTION.errorCode,
    MessageResolver.getMessage("error.field.invalid", fieldName, allowableValues)
) {
  companion object {
    private val EXCEPTION = Exception.FIELD_INVALID
  }
}