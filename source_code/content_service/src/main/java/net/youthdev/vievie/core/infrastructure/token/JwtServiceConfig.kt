package net.youthdev.vievie.core.infrastructure.token

import org.jvnet.hk2.annotations.Contract

@Contract
interface JwtServiceConfig {
  fun getSecretKey(): String

  fun getDebugSecretKey(): String?

  fun isDebugModeEnabled(): Boolean
}