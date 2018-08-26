package net.youthdev.vievie.core.infrastructure.token

import net.youthdev.vievie.core.infrastructure.token.model.TokenModel
import org.jvnet.hk2.annotations.Contract

@Contract
interface RefreshTokenService {
  fun generate(): TokenModel
  fun generate(shouldUseShortExpirytime: Boolean): TokenModel
  fun getExpiryDuration(): Long
}