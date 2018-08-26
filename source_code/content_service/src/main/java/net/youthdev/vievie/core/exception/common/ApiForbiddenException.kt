package net.youthdev.vievie.core.exception.common

import net.youthdev.vievie.application.exception.Exception
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.framework.localization.MessageResolver
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import net.youthdev.vievie.core.utils.Utils

class ApiForbiddenException
  : RestException(
    EXCEPTION.errorCode,
    MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)),
    MessageResolver.getMessage("error.forbidden")
) {
  companion object {
    private val EXCEPTION = Exception.FORBIDDEN
  }
}