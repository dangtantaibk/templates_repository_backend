package net.youthdev.vievie.core.infrastructure.token.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import net.youthdev.vievie.core.exception.token.InvalidJwtException
import net.youthdev.vievie.core.exception.token.JwtExpiredException
import net.youthdev.vievie.core.global.constant.Constants
import net.youthdev.vievie.core.infrastructure.token.JwtService
import net.youthdev.vievie.core.infrastructure.token.JwtServiceConfig
import net.youthdev.vievie.core.infrastructure.token.model.TokenModel
import net.youthdev.vievie.core.utils.Utils
import net.youthdev.vievie.infrastructure.token.model.Permission
import org.apache.commons.logging.LogFactory
import java.io.UnsupportedEncodingException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtServiceImpl @Inject constructor(
    private val jwtServiceConfig: JwtServiceConfig
): JwtService {

  override fun generate(accountId: Int, shouldUseShortExpiryTime: Boolean): TokenModel? {
    var algorithm: Algorithm?
    try {
      algorithm = Algorithm.HMAC256(jwtServiceConfig.getSecretKey())

      val expireTime: Long
      val expiryTimeReturned: Long
      if (!shouldUseShortExpiryTime) {
        expireTime = Utils.currentTimestamp + Constants.JWT_EXPIRY_DURATION
        expiryTimeReturned = Utils.currentTimestamp + 23L * 60 * 60 * 1000
      } else {
        expireTime = Utils.currentTimestamp + 20000 // 20000 = 20s
        expiryTimeReturned = expireTime
      }
      val jwToken = JWT.create()
          .withIssuer(ISSUER)
          .withIssuedAt(Date(Utils.currentTimestamp))
          .withExpiresAt(Date(expireTime))
          .withClaim(ACCOUNT_ID, accountId)
          .withJWTId(UUID.randomUUID().toString())
          .sign(algorithm!!)

      return TokenModel().apply {
        token = jwToken
        this.expireTime = expiryTimeReturned
      }
    } catch (ex: Exception) {
      LOG.error("", ex)
      return null
    }

  }

  override fun generate(accountId: Int): TokenModel? {
    return generate(accountId, false)
  }

  override fun verifyJwtAndRetrieveAccountId(jwt: String): Int {
    try {
      val algorithm = Algorithm.HMAC256(jwtServiceConfig.getSecretKey())
      val jwtVerifier = JWT.require(algorithm).build()
      val decodedJWT = jwtVerifier.verify(jwt)
      return decodedJWT.getClaim(ACCOUNT_ID).asInt()!!
    } catch (ex: UnsupportedEncodingException) {
      throw InvalidJwtException(ex.message)
    } catch (ex: TokenExpiredException) {
      throw JwtExpiredException(jwt)
    } catch (ex: JWTVerificationException) {
      throw InvalidJwtException(jwt)
    }
  }

  override fun generateNurseToken(accountId: Int, isVieVieNurse: Boolean): TokenModel? {
    try {
      val algorithm = Algorithm.HMAC256(jwtServiceConfig.getSecretKey())
      val expireTime = Utils.currentTimestamp + Constants.JWT_EXPIRY_DURATION
      val jwTokenBuilder = JWT.create()
          .withIssuer(ISSUER)
          .withIssuedAt(Date(Utils.currentTimestamp))
          .withExpiresAt(Date(expireTime))
          .withClaim(ACCOUNT_ID, accountId)
          .withJWTId(UUID.randomUUID().toString())

      if (isVieVieNurse) {
        jwTokenBuilder.withArrayClaim("permissions",
            arrayOf("NURSE.ALERT.*", "NURSE.CASE_POOL.*", "NURSE.DOCTOR_PROFILE.*", "NURSE.PATIENT_PROFILE.*", "NURSE.LAB_TEST_APPOINTMENT.*", "NURSE.LAB_TEST.*", "NURSE.LAB_TEST_PROVIDER.*"))
      } else {
        jwTokenBuilder.withArrayClaim("permissions",
            arrayOf("NURSE.LAB_TEST_APPOINTMENT.*"))
      }

      val jwToken = jwTokenBuilder.sign(algorithm)

      return TokenModel().apply {
        this.token = jwToken
        this.expireTime = expireTime
      }
    } catch (ex: Exception) {
      LOG.error("", ex)
      return null
    }
  }

  override fun generateTokenForPublishCommandAuthorization(): String? {
    val algorithm: Algorithm
    try {
      algorithm = Algorithm.HMAC256(jwtServiceConfig.getSecretKey())
    } catch (ex: Exception) {
      LOG.error("Error when generate token for publish command authorization", ex)
      return null
    }

    val expireTime = Utils.currentTimestamp + Constants.PUBLISH_COMMAND_AUTHORIZATION_TOKEN_EXPIRY_DURATION

    return JWT.create()
        .withIssuer(ISSUER)
        .withIssuedAt(Date(Utils.currentTimestamp))
        .withExpiresAt(Date(expireTime))
        .withJWTId(UUID.randomUUID().toString())
        .withArrayClaim(PUBSUB_JWT_PERMISSIONS_CLAIM, arrayOf(PUSSUB_JWT_PERMISSION_SEND_PUBLISH_COMMAND))
        .sign(algorithm)
  }

  override fun isTokenValidFor(jwt: String, vararg permissions: Permission): Boolean {
    return this.isTokenValidFor(jwt, jwtServiceConfig.getSecretKey(), *permissions)
  }

  override fun isTokenValidFor(jwt: String, secretKey: String, vararg permissions: Permission): Boolean {
    try {
      val algorithm = Algorithm.HMAC256(secretKey)
      val jwtVerifier = JWT.require(algorithm).build()
      val decodedJWT = jwtVerifier.verify(jwt)
      val permissionsInArray = decodedJWT.getClaim("permissions").asList(String::class.java)

      return Arrays.stream(permissions)
          .allMatch { it -> permissionsInArray.contains(it.value) }
    } catch (ex: UnsupportedEncodingException) {
      throw InvalidJwtException(ex.message)
    } catch (ex: TokenExpiredException) {
      throw JwtExpiredException(jwt)
    } catch (ex: JWTVerificationException) {
      throw InvalidJwtException(jwt)
    }
  }

  companion object {
    private val LOG = LogFactory.getLog(JwtServiceImpl::class.java)
    private const val ISSUER = "net.youthdev.vievie"
    const val ACCOUNT_ID = "accountId"
    private const val PUBSUB_JWT_PERMISSIONS_CLAIM = "permissions"
    private const val PUSSUB_JWT_PERMISSION_SEND_PUBLISH_COMMAND = "net.youthdev.vievie.PERMISSIONS.SEND_PUBLISH_COMMAND"
  }
}