package net.youthdev.vievie.core.bootstrap.filter

import net.youthdev.vievie.core.framework.localization.LocaleHolder
import net.youthdev.vievie.core.framework.localization.MessageResolver
import net.youthdev.vievie.core.global.constant.Constants
import net.youthdev.vievie.core.global.constant.Headers
import net.youthdev.vievie.core.infrastructure.common.EnvironmentConfig
import net.youthdev.vievie.core.infrastructure.slack.SlackChannel
import net.youthdev.vievie.core.infrastructure.token.JwtService
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.ThreadContext
import java.util.*
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter

class RequestFilter @Inject constructor(
    private val jwtService: JwtService
): ContainerRequestFilter {

  override fun filter(containerRequestContext: ContainerRequestContext) {
    /*
    Handle set locale
     */
    try {
      var locale = if (containerRequestContext.acceptableLanguages.isEmpty())
        Locale.forLanguageTag(MessageResolver.VIEVIE_LANGUAGE_TAG_VN)
      else
        containerRequestContext.acceptableLanguages[0]
      if ("*" == locale.language) {
        locale = Locale.forLanguageTag(MessageResolver.VIEVIE_LANGUAGE_TAG_VN)
      }
      LocaleHolder.set(locale)
    } catch (e: Exception) {
      // Intentionally do nothing?
    }

    // Handle set correlation id
    var correlationId = containerRequestContext.getHeaderString(Headers.CORRELATION_ID)
    if (StringUtils.isBlank(correlationId)) {
      correlationId = UUID.randomUUID().toString()
    }
    ThreadContext.put(Constants.CORRELATION_ID, correlationId)

    // handle authorization
    containerRequestContext.getHeaderString(Headers.AUTHORIZATION)?.let { authToken ->
      jwtService.verifyJwtAndRetrieveAccountId(authToken)
    }
  }
}