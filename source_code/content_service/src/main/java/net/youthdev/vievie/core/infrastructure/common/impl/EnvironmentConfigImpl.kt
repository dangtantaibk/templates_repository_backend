package net.youthdev.vievie.core.infrastructure.common.impl

import net.youthdev.vievie.core.exception.WrappedAppError
import net.youthdev.vievie.core.infrastructure.common.EnvironmentConfig
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
import org.glassfish.hk2.api.Immediate

@Immediate
class EnvironmentConfigImpl: EnvironmentConfig {

  private val isDebugMode: Boolean = BooleanUtils.toBoolean(System.getenv(CONFIG_IS_DEBUG))
  private val mediaUrl: String?
  private val errorReportWebhookUrl: String
  private val shouldDoBackgroundWorkds: Boolean = BooleanUtils.toBoolean(System.getenv(CONFIG_SHOULD_DO_BACKGROUND_WORKS))

  init {
    mediaUrl = StringUtils.appendIfMissing(System.getenv(CONFIG_MEDIA_URL), "/")
    if (mediaUrl.isNullOrBlank()) {
      throw WrappedAppError("Failed to get value from $CONFIG_MEDIA_URL environment variable.")
    }
    errorReportWebhookUrl = System.getenv(CONFIG_ERROR_REPORT_WEBHOOK_URL) ?: StringUtils.EMPTY
  }

  override fun getIsDebug(): Boolean {
    return isDebugMode
  }

  override fun getMediaUrl(): String {
    return mediaUrl!!
  }

  override fun formatMediaUrl(relativeUrl: String): String {
    if (relativeUrl.isNullOrEmpty() || relativeUrl.startsWith("http")) {
      return relativeUrl
    }
    val mediaRootUrl = mediaUrl
    return mediaRootUrl.plus(relativeUrl)
  }

  override fun getErrorReportWebhookUrl(): String {
    return errorReportWebhookUrl
  }

  override fun getShouldDoBackgroundWorks(): Boolean {
    return shouldDoBackgroundWorkds
  }

  companion object {
    private const val CONFIG_IS_DEBUG = "VIEVIE_IS_DEBUG"
    private const val CONFIG_MEDIA_URL = "VIEVIE_MEDIA_URL"
    private const val CONFIG_ERROR_REPORT_WEBHOOK_URL = "VIEVIE_ERROR_REPORT_WEBHOOK_URL"
    private const val CONFIG_SHOULD_DO_BACKGROUND_WORKS = "SHOULD_DO_BACKGROUND_WORKS"
  }
}