package net.youthdev.vievie.core.exception

import org.apache.commons.lang3.StringUtils
import javax.ws.rs.core.Response

open class RestException: RuntimeException {
  var errorCode: String? = null
  var msg: String? = null
  var debug: String? = null
  var trace: String? = null
  var httpStatus: Response.Status = Response.Status.BAD_REQUEST

  constructor(errorCode: String) {
    this.errorCode = errorCode
  }

  constructor(errorCode: String, msg: String): super(msg) {
    this.errorCode = errorCode
    this.msg = msg
  }

  constructor(errorCode: String, msg: String, debug: String): super(if (StringUtils.isNotBlank(debug)) debug else msg) {
    this.errorCode = errorCode
    this.msg = msg
    this.debug = debug
  }

  constructor(
      errorCode: String, msg: String, debug: String, trace: String
  ): super(if (StringUtils.isNotBlank(debug)) debug else msg) {
    this.errorCode = errorCode
    this.msg = msg
    this.debug = debug
    this.trace = trace
  }

  constructor(
      errorCode: String, msg: String, debug: String, trace: String, httpStatus: Response.Status
  ): super(if (StringUtils.isNotBlank(debug)) debug else msg) {
    this.errorCode = errorCode
    this.msg = msg
    this.debug = debug
    this.trace = trace
    this.httpStatus = httpStatus
  }

  fun toErrorModel(): RestErrorModel {
    return RestErrorModel(errorCode, msg, debug, trace)
  }

  fun withHttpStatus(status: Response.Status): RestException {
    this.httpStatus = status
    return this
  }
}