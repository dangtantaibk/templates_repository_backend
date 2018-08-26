package net.youthdev.vievie.core.exception.token

import net.youthdev.vievie.application.exception.Exception
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.framework.localization.MessageResolver
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import net.youthdev.vievie.core.utils.Utils
import org.apache.commons.lang3.StringUtils

class InvalidJwtException(
    debug: String?
) : RestException(
    EXCEPTION.errorCode,
    MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)),
    MessageResolver.getMessage("error.invalid_jwt"),
    debug ?: StringUtils.EMPTY
) {
  companion object {
    private val EXCEPTION = Exception.INVALID_JWT
  }
}