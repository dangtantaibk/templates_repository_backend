package net.youthdev.vievie.core.bootstrap.filter

import net.youthdev.vievie.core.framework.localization.LocaleHolder
import net.youthdev.vievie.core.framework.rest.RestResponse
import org.apache.logging.log4j.ThreadContext
import java.io.ByteArrayOutputStream
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter

class ResponseFilter: ContainerResponseFilter {

  override fun filter(p0: ContainerRequestContext?, containerResponseContext: ContainerResponseContext) {
    // map response with custom headers
    containerResponseContext.entity?.let { responseEntity ->
      when (responseEntity) {
        is RestResponse<*> -> {
          if (responseEntity.getResponse() != null && responseEntity.getResponse() is ByteArrayOutputStream) {
            containerResponseContext.entity = (responseEntity.getResponse() as ByteArrayOutputStream).toByteArray()
          }
          responseEntity.getHeaders().forEach { key, value ->
            containerResponseContext.headers.add(key, value)
          }
        }
      }
    }

    // clean up locale
    LocaleHolder.remove()

    // clean up correlation id
    ThreadContext.clearAll()
  }
}