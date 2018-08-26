package net.youthdev.vievie.core.infrastructure.token

import net.youthdev.vievie.core.infrastructure.token.model.TokenModel
import net.youthdev.vievie.infrastructure.token.model.Permission
import org.jvnet.hk2.annotations.Contract

@Contract
interface JwtService {
  fun generate(accountId: Int, shouldUseShortExpiryTime: Boolean): TokenModel?
  fun generate(accountId: Int): TokenModel?

  fun verifyJwtAndRetrieveAccountId(jwt: String): Int

  fun generateNurseToken(accountId: Int, isVieVieNurse: Boolean): TokenModel?

  fun generateTokenForPublishCommandAuthorization(): String?
  fun isTokenValidFor(jwt: String, vararg permissions: Permission): Boolean

  fun isTokenValidFor(jwt: String, secretKey: String, vararg permissions: Permission): Boolean
}