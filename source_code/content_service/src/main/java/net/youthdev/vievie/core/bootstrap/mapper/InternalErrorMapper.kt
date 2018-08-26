package net.youthdev.vievie.core.bootstrap.mapper

import net.youthdev.vievie.core.exception.RestErrorModel
import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.logging.LogFactory
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class InternalErrorMapper: ExceptionMapper<Throwable> {

  override fun toResponse(throwable: Throwable): Response {
    return try {
      LOG.error("Unhandled error: " + throwable.message, throwable)
      val error = RestErrorModel(
          ERROR_INTERNAL, ERROR_INTERNAL_MSG, throwable.message, ExceptionUtils.getStackTrace(throwable))
      ResponseTemplate.internalError(error)
    } catch (ex: Exception) {
      ResponseTemplate.internalError(RestErrorModel(ERROR_INTERNAL, ERROR_INTERNAL_MSG, "", ""))
    }

  }

  companion object {
    private val LOG = LogFactory.getLog(InternalErrorMapper::class.java)

    private const val ERROR_INTERNAL = "INTERNAL_SERVER_ERROR"
    private const val ERROR_INTERNAL_MSG = "An unexpected error has occurred on our server, please contact us for support."
  }
}