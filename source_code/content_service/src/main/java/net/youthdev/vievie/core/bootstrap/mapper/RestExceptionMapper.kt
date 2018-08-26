package net.youthdev.vievie.core.bootstrap.mapper

import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class RestExceptionMapper: ExceptionMapper<RestException> {
  override fun toResponse(e: RestException): Response {
    return ResponseTemplate.error(e.toErrorModel(), e.httpStatus)
  }
}