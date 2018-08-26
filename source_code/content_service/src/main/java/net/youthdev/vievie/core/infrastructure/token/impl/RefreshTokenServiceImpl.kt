package net.youthdev.vievie.core.infrastructure.token.impl

import net.youthdev.vievie.core.global.constant.Constants
import net.youthdev.vievie.core.infrastructure.token.RefreshTokenService
import net.youthdev.vievie.core.infrastructure.token.model.TokenModel
import net.youthdev.vievie.core.utils.Utils
import java.util.*
import javax.inject.Singleton

@Singleton
class RefreshTokenServiceImpl: RefreshTokenService {
  override fun generate(): TokenModel {
    return generate(false)
  }

  override fun generate(shouldUseShortExpirytime: Boolean): TokenModel {
    val token = UUID.randomUUID().toString()
    val expireTime: Long
    if (!shouldUseShortExpirytime) {
      expireTime = Utils.currentTimestamp + Constants.REFRESH_TOKEN_EXPIRY_DURATION
    } else {
      expireTime = Utils.currentTimestamp + 5 * 60000 // Expire in 5 mins
    }

    return TokenModel().apply {
      this.token = token
      this.expireTime = expireTime
    }
  }

  override fun getExpiryDuration(): Long {
    return Constants.REFRESH_TOKEN_EXPIRY_DURATION
  }
}