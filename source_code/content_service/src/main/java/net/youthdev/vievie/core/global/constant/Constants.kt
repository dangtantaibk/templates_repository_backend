package net.youthdev.vievie.core.global.constant

object Constants {
  const val AUTHORIZATION_HEADER_NAME = "Authorization"

  const val JWT_EXPIRY_DURATION = 30L * 24 * 60 * 60 * 1000

  const val FORGOT_PASSWORD_JWT_EXPIRY_DURATION = 5L * 60 * 1000

  const val TWILIO_ACCESS_TOKEN_EXPIRY_DURATION = 23 * 60 * 60

  const val CALL_TOKEN_EXPIRY_DURATION = 5 * 60 * 1000

  const val REFRESH_TOKEN_EXPIRY_DURATION = 30L * 24 * 60 * 60 * 1000

  const val PUBLISH_COMMAND_AUTHORIZATION_TOKEN_EXPIRY_DURATION = 5 * 60 * 1000L

  const val UTF_8 = "UTF-8"

  const val COMMA = ","

  const val SEMICOLON = ";"

  const val TAB = "\\t"

  const val CORRELATION_ID = "CORRELATION_ID"

  const val DASH = "-"

  const val ASTERISK = "*"

  const val ONE_DAY_IN_MILLISECONDS = 24L * 60 * 60 * 1000
  const val ONE_HOUR_IN_MILLISECONDS = 60L * 60 * 1000
  const val ONE_MINUTE_IN_MILLISECONDS = 60L * 1000
}