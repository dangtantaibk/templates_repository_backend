package net.youthdev.vievie.core.infrastructure.common

import org.jvnet.hk2.annotations.Contract

@Contract
interface EnvironmentConfig {
  fun getIsDebug(): Boolean
  fun getMediaUrl(): String
  fun formatMediaUrl(relativeUrl: String): String
  fun getErrorReportWebhookUrl(): String
  fun getShouldDoBackgroundWorks(): Boolean
}