package net.youthdev.vievie.core.bootstrap.mapper

import net.youthdev.vievie.core.exception.RestErrorModel
import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import org.apache.commons.lang3.exception.ExceptionUtils
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class ValidationExceptionMapper: ExceptionMapper<ValidationException> {
  override fun toResponse(e: ValidationException): Response {
    if (e is ConstraintViolationException) {
      val firstConstraintViolation = e.constraintViolations.stream().findFirst().orElse(null)
      if (firstConstraintViolation != null) {
        return ResponseTemplate.badRequest(RestErrorModel(ERROR_CODE, firstConstraintViolation.message, "", ExceptionUtils.getStackTrace(e)))
      }
    }

    return ResponseTemplate.badRequest(RestErrorModel(ERROR_CODE, e.message, "", ExceptionUtils.getStackTrace(e)))
  }

  companion object {
    private const val ERROR_CODE = "VALIDATION_ERROR"
  }
}