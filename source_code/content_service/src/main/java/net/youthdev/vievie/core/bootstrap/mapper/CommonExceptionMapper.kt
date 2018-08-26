package net.youthdev.vievie.core.bootstrap.mapper

import net.youthdev.vievie.core.framework.rest.ResponseTemplate
import net.youthdev.vievie.core.infrastructure.common.EnvironmentConfig
import net.youthdev.vievie.core.infrastructure.slack.SlackChannel
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.logging.LogFactory
import javax.inject.Inject
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class CommonExceptionMapper @Inject constructor(
    private val slackChannel: SlackChannel,
    private val environmentConfig: EnvironmentConfig
): ExceptionMapper<Exception> {
  override fun toResponse(ex: Exception): Response {
    // push to slack webhook
    slackChannel.pushToChannelAsync(
        environmentConfig.getErrorReportWebhookUrl(),
        "Error: ${ex.message}, stack trace:%n```${ExceptionUtils.getStackTrace(ex).replace("`", StringUtils.EMPTY)}```")
    LOG.error(ex)
    return ResponseTemplate.internalError(ex)
  }

  companion object {
    private val LOG = LogFactory.getLog(CommonExceptionMapper::class.java)
  }
}