package net.youthdev.vievie.core.exception

import kotlin.Exception

class WrappedAppError: RuntimeException {

  constructor(ex: Exception): super(ex)

  constructor(message: String, ex: Exception): super(message, ex)

  constructor(message: String): super(message)
}