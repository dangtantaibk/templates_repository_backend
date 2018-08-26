package net.youthdev.vievie.core.framework.rest

import java.util.HashMap

class RestResponse<R> {
  private var response: R? = null
  private var headers: MutableMap<String, String> = HashMap()

  fun <R> create(response: R): RestResponse<R> {
    val model = RestResponse<R>()
    model.setResponse(response)
    return model
  }

  fun createEmpty(): RestResponse<Void> {
    val model = RestResponse<Void>()
    model.setResponse(null)
    return model
  }

  fun getResponse(): R? {
    return response
  }

  fun setResponse(response: R?) {
    this.response = response
  }

  fun getHeaders(): Map<String, String> {
    return headers
  }

  fun setHeaders(headers: MutableMap<String, String>) {
    this.headers = headers
  }

  fun putHeader(header: String, value: String): RestResponse<R> {
    headers[header] = value
    return this
  }
}