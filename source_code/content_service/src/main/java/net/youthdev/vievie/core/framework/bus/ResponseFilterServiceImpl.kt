package net.youthdev.vievie.core.framework.bus

import net.youthdev.vievie.core.exception.DomainRuleViolatedException
import net.youthdev.vievie.core.exception.Error
import net.youthdev.vievie.core.exception.RestErrorModel
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.exception.token.InvalidJwtException
import net.youthdev.vievie.core.framework.localization.LocaleHolder
import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import net.youthdev.vievie.core.framework.rest.RestResponse
import net.youthdev.vievie.core.infrastructure.common.EnvironmentConfig
import net.youthdev.vievie.core.infrastructure.slack.SlackChannel
import net.youthdev.vievie.core.infrastructure.token.JwtService
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.logging.LogFactory
import org.glassfish.grizzly.http.server.Request
import java.io.ByteArrayOutputStream
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.core.Response

@Singleton
@Deprecated("Legacy response filter is deprecated")
class ResponseFilterServiceImpl @Inject constructor(
    private val jwtService: JwtService,
    private val slackChannel: SlackChannel,
    private val environmentConfig: EnvironmentConfig
) : ResponseFilterService {

  override fun <T> filter(request: Request, action: Callable<T>): Response {
    try {
      LocaleHolder.set(request.locale)
      val result = action.call() ?: return ResponseTemplate.empty()

      if (result is RestResponse<*>) {
        val restResponse = result as RestResponse<*>
        return if (restResponse.getResponse() != null) {
          if (restResponse.getResponse() is ByteArrayOutputStream) {
            ResponseTemplate.response(
                (restResponse.getResponse() as ByteArrayOutputStream).toByteArray(),
                Response.Status.OK,
                restResponse.getHeaders()
            )
          } else {
            ResponseTemplate.response(restResponse.getResponse()!!, Response.Status.OK, restResponse.getHeaders())
          }
        } else {
          ResponseTemplate.empty(restResponse.getHeaders())
        }
      }

      return ResponseTemplate.ok(result)
    } catch (ex: RestException) {
      return ResponseTemplate.error(ex.toErrorModel(), ex.httpStatus)
    } catch (ex: Error) {
      return ResponseTemplate.badRequest(RestErrorModel(ex.code, ex.msg, StringUtils.EMPTY, ExceptionUtils.getStackTrace(ex)))
    } catch (ex: DomainRuleViolatedException) {
      LOG.error("Error: ${ex.message}, stack trace:%n${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}")
      return ResponseTemplate.badRequest(RestErrorModel("DOMAIN_RULE_VIOLATED", ex.message, StringUtils.EMPTY, StringUtils.EMPTY))
    } catch (ex: Exception) {
      // push to slack webhook
      slackChannel.pushToChannelAsync(
          environmentConfig.getErrorReportWebhookUrl(),
          "Error: ${ex.message}, stack trace:%n```${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}```")
      LOG.error(ex)
      return  ResponseTemplate.internalError(ex)
    }
  }

  override fun <T> filter(request: Request, accountId: Int, jwt: String, action: Callable<T>): Response {
    try {
      LocaleHolder.set(request.locale)
      if (jwt.isNullOrBlank()) {
        throw RestException("AUTH_TOKEN_MISSING", "Authorization token is missing")
      }
      if (accountId != jwtService.verifyJwtAndRetrieveAccountId(jwt)) {
        throw InvalidJwtException("Required accountId and claimed accountId does not match: $accountId@$jwt")
      }
    } catch (ex: RestException) {
      return ResponseTemplate.error(ex.toErrorModel(), ex.httpStatus)
    } catch (ex: DomainRuleViolatedException) {
      LOG.error("Error: ${ex.message}, stack trace:%n${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}")
      return ResponseTemplate.badRequest(RestErrorModel("DOMAIN_RULE_VIOLATED", ex.message, StringUtils.EMPTY, StringUtils.EMPTY))
    } catch (ex: Exception) {
      // push to slack webhook
      slackChannel.pushToChannelAsync(
          environmentConfig.getErrorReportWebhookUrl(),
          "Error: ${ex.message}, stack trace:%n```${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}```")
      LOG.error(ex)
      return  ResponseTemplate.internalError(ex)
    }

    return filter(request, action)
  }

  override fun <T> filter(request: Request, jwt: String, action: Callable<T>): Response {
    try {
      LocaleHolder.set(request.locale)
      if (jwt.isNullOrBlank()) {
        throw RestException("AUTH_TOKEN_MISSING", "Authorization token is missing")
      }
      jwtService.verifyJwtAndRetrieveAccountId(jwt)
    } catch (ex: RestException) {
      return ResponseTemplate.error(ex.toErrorModel(), ex.httpStatus)
    } catch (ex: DomainRuleViolatedException) {
      LOG.error("Error: ${ex.message}, stack trace:%n${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}")
      return ResponseTemplate.badRequest(RestErrorModel("DOMAIN_RULE_VIOLATED", ex.message, StringUtils.EMPTY, StringUtils.EMPTY))
    } catch (ex: Exception) {
      // push to slack webhook
      slackChannel.pushToChannelAsync(
          environmentConfig.getErrorReportWebhookUrl(),
          "Error: ${ex.message}, stack trace:%n```${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}```")
      LOG.error(ex)
      return  ResponseTemplate.internalError(ex)
    }

    return filter(request, action)
  }

  companion object {
    private val LOG = LogFactory.getLog(ResponseFilterServiceImpl::class.java)
  }
}