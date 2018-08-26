package net.youthdev.vievie.core.bootstrap.mapper

import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider
import net.youthdev.vievie.core.exception.Error
import net.youthdev.vievie.core.exception.RestErrorModel
import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import javax.ws.rs.core.Response

@Provider
class ErrorMapper: ExceptionMapper<Error> {
  override fun toResponse(e: Error): Response {
    return ResponseTemplate.badRequest(RestErrorModel(e.code, e.msg, StringUtils.EMPTY, ExceptionUtils.getStackTrace(e)))
  }
}