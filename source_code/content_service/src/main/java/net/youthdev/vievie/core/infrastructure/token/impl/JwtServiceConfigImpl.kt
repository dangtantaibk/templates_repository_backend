package net.youthdev.vievie.core.infrastructure.token.impl

import net.youthdev.vievie.core.exception.WrappedAppError
import net.youthdev.vievie.core.infrastructure.token.JwtServiceConfig
import org.apache.commons.lang3.StringUtils
import org.glassfish.hk2.api.Immediate

@Immediate
class JwtServiceConfigImpl: JwtServiceConfig {
  private val secretKey: String? = System.getenv("VIEVIE_JWT_SECRET_KEY")
  private val debugSecretKey: String? = System.getenv("VIEVIE_JWT_DEBUG_SECRET_KEY")

  init {
    if (secretKey.isNullOrEmpty()) {
      throw WrappedAppError("Can not get environment variable VIEVIE_JWT_SECRET_KEY")
    }
  }

  override fun getSecretKey(): String {
    return secretKey!!
  }

  override fun getDebugSecretKey(): String? {
    return debugSecretKey
  }

  override fun isDebugModeEnabled(): Boolean {
    return StringUtils.isNotEmpty(debugSecretKey)
  }
}