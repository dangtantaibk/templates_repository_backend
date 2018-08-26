package net.youthdev.vievie.application.exceptions.common

import net.youthdev.vievie.application.exception.Exception
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.framework.localization.MessageResolver
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import net.youthdev.vievie.core.utils.Utils

class NotSupportedOperationException(
    debug: String
) : RestException(
    EXCEPTION.errorCode,
    MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)),
    MessageResolver.getMessage("error.not_supported_operation"),
    debug
) {
  companion object {
    private val EXCEPTION = Exception.NOT_SUPPORTED_OPERATION
  }
}