package net.youthdev.vievie.application.exceptions.common

import net.youthdev.vievie.application.exception.Exception
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.framework.localization.MessageResolver
import net.youthdev.vievie.core.global.constant.DateTimePatterns
import net.youthdev.vievie.core.utils.Utils

class ResourceNotExistedException : RestException {

  constructor(debug: String)
      : super(EXCEPTION.errorCode, MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)), debug, "")

  constructor(resourceName: String, resourceId: Int)
      : super(
      EXCEPTION.errorCode,
      MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)),
      MessageResolver.getMessage("error.resource_not_exist.with_id", resourceName, resourceId)
  )

  constructor(resourceClass: Class<*>, resourceId: Int)
      : super(
      EXCEPTION.errorCode,
      MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)),
      MessageResolver.getMessage("error.resource_not_exist.with_id", resourceClass.simpleName, resourceId)
  )

  constructor(resourceClass: Class<*>, resourceIdentifier: String)
      : super(
      EXCEPTION.errorCode,
      MessageResolver.getMessage("error.base", EXCEPTION.responseCode, Utils.getCurrentDateTime(DateTimePatterns.DISPLAY_DATETIME_ERROR_PATTERN)),
      MessageResolver.getMessage("error.resource_not_exist.with_identifier", resourceClass.simpleName, resourceIdentifier)
  )

  companion object {
    private val EXCEPTION = Exception.RESOURCE_NOT_EXISTED
  }
}
