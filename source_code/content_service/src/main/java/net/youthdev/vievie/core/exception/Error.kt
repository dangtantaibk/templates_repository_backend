package net.youthdev.vievie.core.exception

open class Error(val code: String, val msg: String, val displayMsg: String? = null, cause: Throwable? = null)
  : RuntimeException(msg, cause) {
  constructor(msg: String, cause: Throwable? = null): this("UNKNOWN_ERROR", msg, null, cause)
}