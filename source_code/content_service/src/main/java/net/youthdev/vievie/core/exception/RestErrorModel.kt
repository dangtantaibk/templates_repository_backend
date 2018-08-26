package net.youthdev.vievie.core.exception

class RestErrorModel {
  var errorCode: String? = null
  var msg: String? = null
  var debug: String? = null
  var trace: String? = null

  constructor(
      errorCode: String?,
      msg: String?,
      debug: String?,
      trace: String?
  ) {
    this.errorCode = errorCode
    this.msg = msg
    this.debug = debug
    this.trace = trace
  }
}