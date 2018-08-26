package net.youthdev.vievie.core.framework.rest

import net.youthdev.vievie.core.exception.RestErrorModel
import net.youthdev.vievie.core.global.constant.Headers
import org.apache.commons.lang3.exception.ExceptionUtils
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

class ResponseTemplate {
  companion object {
    private val INTERNAL_ERROR_CODE = "internal_server_error"
    private val INTERNAL_ERROR_MSG = "There are some error occurred on the server. Please contact us for support."
    private val HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS = "x-pagination-count, x-updated-assignment-state, x-updated-case-state, x-pagination-item-count, x-total-element"
    private val HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS = "Authorization, Content-Type, X-Last-Modified, " +
        "X-Profile-Completed-Case-Id, X-Pending-Assigned-Doctor-Id, X-Case-Event-Local-Id, Accept, Accept-Language, " +
        "Accept-Encoding"

    fun response(entity: Any, status: Int): Response {
      return Response.status(status)
          .entity(entity)
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
          .build()
    }

    fun response(entity: Any, status: Response.Status): Response {
      return Response.status(status)
          .entity(entity)
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
          .build()
    }

    fun response(entity: Any, status: Response.Status, headers: Map<String, String>): Response {
      val builder = Response.status(status)
          .entity(entity)
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
      for ((key, value) in headers) {
        builder.header(key, value)
      }
      return builder.build()
    }

    fun ok(entity: Any): Response {
      return response(entity, Response.Status.OK)
    }

    fun empty(): Response {
      return Response.status(Response.Status.OK)
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
          .build()
    }

    fun empty(headers: Map<String, String>): Response {
      val builder = Response
          .status(Response.Status.OK)
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
      for ((key, value) in headers) {
        builder.header(key, value)
      }
      return builder.build()
    }

    fun badRequest(entity: Any): Response {
      return response(entity, Response.Status.BAD_REQUEST)
    }

    fun internalError(throwable: Throwable): Response {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .type(MediaType.APPLICATION_JSON)
          .entity(RestErrorModel(INTERNAL_ERROR_CODE, INTERNAL_ERROR_MSG, "", ExceptionUtils.getStackTrace(throwable)))
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
          .build()
    }

    fun internalError(entity: Any): Response {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .type(MediaType.APPLICATION_JSON)
          .entity(entity)
          .header(Headers.ACCESS_CONTROL_EXPOSE_HEADER, HEADER_VALUE_ACCESS_CONTROL_EXPOSE_HEADERS)
          .header(Headers.ACCESS_CONTROL_ALLOWED_HEADERS, HEADER_VALUE_ACCESS_CONTROL_ALLOW_HEADERS)
          .build()
    }

    fun error(entity: Any, status: Response.Status): Response {
      return response(entity, status)
    }
  }
}